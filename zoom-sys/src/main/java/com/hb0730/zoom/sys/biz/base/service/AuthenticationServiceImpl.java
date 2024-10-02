package com.hb0730.zoom.sys.biz.base.service;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.convert.Convert;
import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.security.UserInfo;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.util.AesCryptoUtil;
import com.hb0730.zoom.base.util.JsonUtil;
import com.hb0730.zoom.base.util.Md5Util;
import com.hb0730.zoom.base.util.StrUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.config.AuthenticationConfig;
import com.hb0730.zoom.security.core.service.UserService;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginToken;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginTokenIdentity;
import com.hb0730.zoom.sys.biz.system.service.SysUserService;
import com.hb0730.zoom.sys.define.cache.UserCacheKeyDefine;
import com.hb0730.zoom.sys.enums.LoginTokenStatusEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 用户认证
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements UserService {
    private final CacheUtil cache;
    private final AuthenticationConfig authConfig;
    private final SysUserService userService;

    /**
     * 用户登录
     *
     * @param loginInfo 登录信息
     * @return token
     */
    public R<String> login(LoginInfo loginInfo) {
        // 登陆失败次数
        String loginFailureCount = UserCacheKeyDefine.LOGIN_FAILURE.format(loginInfo.getUsername());
        // 多次认证失败禁止登录
        if (Convert.toInt(cache.getString(loginFailureCount), 0) >= authConfig.getLoginFailedLockCount()) {
            return R.NG("账号已被锁定，请联系管理员重置密码");
        }
        SysUser user = userService.findByUsername(loginInfo.getUsername());
        if (user == null) {
            // 记录登录失败次数
            return R.NG("用户不存在");
        }
        // 1. 校验用户是否有效
        R<?> res = userService.checkUserIsEffective(user);
        if (!res.isSuccess()) {
            cache.incr(loginFailureCount, 1);
            // 记录登录失败次数
            return R.NG(res.getMessage());
        }

        // 2. 校验用户名或密码是否正确
        String _password = Md5Util.md5Hex(loginInfo.getPassword(), user.getSalt());
        if (!_password.equals(user.getPassword())) {
            // 记录登录失败次数
            cache.incr(loginFailureCount, 1);
            return R.NG("用户名或密码错误");
        }
        // 3. 设置上次登录时间
        userService.updateLastLoginTime(user.getId());

        // 4. 删除用户缓存
        this.delUserCache(user);

        // 5. 重设用户缓存
        this.setUserCache(user);

        long current = System.currentTimeMillis();

        // 6. 不允许多端登录
        if (!authConfig.getAllowMultiDevice()) {
            // 无效化其他缓存
            this.invalidOtherDeviceToken(user, current);
        }
        // 7. 生成token
        String token = this.generatorLoginToken(user, current);
        return R.OK(token);
    }

    @Override
    public UserInfo getUserByToken(String token) {
        LoginToken loginToken = getLoginToken(token);
        if (loginToken == null) {
            return null;
        }
        // 检测状态
        if (loginToken.getStatus() != 1) {
            // 删除缓存
            UserCacheKeyDefine.LOGIN_TOKEN.format(loginToken.getId(), loginToken.getOrigin().getLoginTime());
            return null;
        }
        // 获取登录信息
        SysUser user = userService.getById(loginToken.getId());
        if (user == null) {
            return null;
        }
        // 获取用户信息
        UserInfo userInfo = getUserInfoById(user.getId());
        if (userInfo == null) {
            return null;
        }
        // 检查用户状态
        // 更新登录时间
        return userInfo;
    }

    /**
     * 用户登出
     *
     * @param token token
     */
    public void logout(String token) {
        if (StrUtil.isBlank(token)) {
            return;
        }
        Pair<String, Long> parseToken = parseToken(token);
        if (parseToken == null) {
            return;
        }
        String id = parseToken.getCode();
        Long current = parseToken.getMessage();
        // 删除 loginToken & refreshToken
        cache.del(UserCacheKeyDefine.LOGIN_TOKEN.format(id, current),
                UserCacheKeyDefine.LOGIN_REFRESH.format(id, current));
    }

    /**
     * 获取loginToken 信息
     *
     * @param token token
     * @return token
     */
    private LoginToken getLoginToken(String token) {
        Pair<String, Long> tokenPair = parseToken(token);
        if (tokenPair == null) {
            return null;
        }
        // 获取登录 key value
        String tokenCacheKey = UserCacheKeyDefine.LOGIN_TOKEN.format(tokenPair.getCode(), tokenPair.getMessage());
        Optional<String> tokenCacheValue = cache.getString(tokenCacheKey);
        if (tokenCacheValue.isPresent()) {
            return JsonUtil.DEFAULT.json2Obj(tokenCacheValue.get(), LoginToken.class);
        }
        // loginToken 不存在 需要查询 refreshToken
        if (!authConfig.getAllowRefresh()) {
            return null;
        }
        String refreshTokenCacheKey = UserCacheKeyDefine.LOGIN_REFRESH.format(tokenPair.getCode(), tokenPair.getMessage());
        Optional<String> refreshTokenCacheValue = cache.getString(refreshTokenCacheKey);
        // refreshToken 不存在
        if (refreshTokenCacheValue.isEmpty()) {
            return null;
        }
        // 执行续签操作
        LoginToken refreshToken = JsonUtil.DEFAULT.json2Obj(refreshTokenCacheValue.get(), LoginToken.class);
        int refreshCount = refreshToken.getRefreshCount() + 1;
        refreshToken.setRefreshCount(refreshCount);
        // 设置登录缓存
        cache.setJson(tokenCacheKey, refreshToken, UserCacheKeyDefine.LOGIN_TOKEN.getTimeout(), UserCacheKeyDefine.LOGIN_TOKEN.getUnit());
        if (refreshCount < authConfig.getRefreshMaxCount()) {
            // 小于续签最大次数 则再次设置 refreshToken
            cache.setJson(refreshTokenCacheKey, refreshToken, UserCacheKeyDefine.LOGIN_REFRESH.getTimeout(), UserCacheKeyDefine.LOGIN_REFRESH.getUnit());

        } else {
            // 大于等于续签最大次数 则删除
            cache.del(refreshTokenCacheKey);
        }
        return refreshToken;
    }

    /**
     * 删除用户缓存
     *
     * @param user 用户
     */
    private void delUserCache(SysUser user) {
        // 用户信息缓存
        String userCacheKey = UserCacheKeyDefine.USER_INFO.format(user.getId());
        // 登录失败次数缓存
        String loginFailureCount = UserCacheKeyDefine.LOGIN_FAILURE.format(user.getUsername());
        cache.del(userCacheKey, loginFailureCount);
    }

    /**
     * 设置用户缓存
     *
     * @param user 用户
     */
    private UserInfo setUserCache(SysUser user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        // 用户信息缓存
        String userCacheKey = UserCacheKeyDefine.USER_INFO.format(user.getId());
        // 查询用户角色
        // 缓存
        cache.setJson(userCacheKey, userInfo, UserCacheKeyDefine.USER_INFO.getTimeout(),
                UserCacheKeyDefine.USER_INFO.getUnit());

        return userInfo;
    }

    /**
     * 无效化其他设备
     *
     * @param user      用户
     * @param loginTime 登录时间
     */
    private void invalidOtherDeviceToken(SysUser user, long loginTime) {
        String tokenCacheKey = UserCacheKeyDefine.LOGIN_TOKEN.format(user.getId(), "*");
        Optional<Set<String>> tokenCacheKeys = cache.scanKeys(tokenCacheKey);
        if (tokenCacheKeys.isPresent()) {
            // 修改登录信息
            List<String> values = cache.getString(tokenCacheKeys.get())
                    .orElse(new ArrayList<>());
            // 获取有效登录信息
            List<LoginToken> loginTokenInfoList = values
                    .stream()
                    .filter(Objects::nonNull)
                    .map(val -> JsonUtil.DEFAULT.json2Obj(val, LoginToken.class))
                    .filter(val -> LoginTokenStatusEnums.OK.getCode().equals(val.getStatus()))
                    .toList();
            // 修改登录信息
            for (LoginToken loginTokenInfo : loginTokenInfoList) {
                String deviceLoginKey = UserCacheKeyDefine.LOGIN_TOKEN.format(user.getId(),
                        loginTokenInfo.getOrigin().getLoginTime());
                loginTokenInfo.setStatus(LoginTokenStatusEnums.OTHER_DEVICE.getCode());
                loginTokenInfo.setOverride(new LoginTokenIdentity(loginTime));
                cache.setJson(deviceLoginKey, loginTokenInfo, UserCacheKeyDefine.LOGIN_TOKEN.getTimeout(),
                        UserCacheKeyDefine.LOGIN_TOKEN.getUnit());
            }

        }
        // 删除续签信息
        String refreshTokenCacheKey = UserCacheKeyDefine.LOGIN_REFRESH.format(user.getId(), "*");
        cache.delScan(refreshTokenCacheKey);
    }

    /**
     * 生成token
     *
     * @param user      用户
     * @param loginTime 登录时间
     * @return token
     */
    private String generatorLoginToken(SysUser user, long loginTime) {
        String id = user.getId();
        // 生成 loginToken
        String loginKey = UserCacheKeyDefine.LOGIN_TOKEN.format(id, loginTime);
        LoginToken loginToken = new LoginToken();
        loginToken.setId(id);
        loginToken.setStatus(LoginTokenStatusEnums.OK.getCode());
        loginToken.setRefreshCount(0);
        // 设置原始登录身份
        loginToken.setOrigin(new LoginTokenIdentity(loginTime));
        cache.setJson(loginKey, loginToken, UserCacheKeyDefine.LOGIN_TOKEN.getTimeout(), UserCacheKeyDefine.LOGIN_TOKEN.getUnit());
        // 生成 refreshToken
        if (authConfig.getAllowRefresh()) {
            String refreshKey = UserCacheKeyDefine.LOGIN_REFRESH.format(id, loginTime);
            cache.setJson(refreshKey, loginToken, UserCacheKeyDefine.LOGIN_REFRESH.getTimeout(), UserCacheKeyDefine.LOGIN_REFRESH.getUnit());
        }
        // 返回token
        String encrypt = AesCryptoUtil.encrypt(id + ":" + loginTime);
        return Base62.encode(encrypt);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return id, loginTime
     */
    private Pair<String, Long> parseToken(String token) {
        try {
            if (StrUtil.isBlank(token)) {
                return null;
            }
            String decrypt = AesCryptoUtil.decrypt(Base62.decodeStr(token));
            String[] split = decrypt.split(":");
            return new Pair<>(split[0], Convert.toLong(split[1]));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    private UserInfo getUserInfoById(String id) {
        String userInfoKey = UserCacheKeyDefine.USER_INFO.format(id);
        Optional<String> userInfoValue = cache.getString(userInfoKey);
        if (userInfoValue.isPresent()) {
            return JsonUtil.DEFAULT.json2Obj(userInfoValue.get(), UserInfo.class);
        }
        // 查询用户信息
        SysUser user = userService.getById(id);
        if (user == null) {
            return null;
        }
        // 缓存用户信息
        return setUserCache(user);
    }
}
