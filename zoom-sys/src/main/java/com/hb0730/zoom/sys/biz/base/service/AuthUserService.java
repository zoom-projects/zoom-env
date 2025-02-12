package com.hb0730.zoom.sys.biz.base.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.meta.UserInfo;
import com.hb0730.zoom.base.sys.system.entity.SysUserSocial;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.sys.biz.base.model.request.RestEmailOrPhoneRequest;
import com.hb0730.zoom.sys.biz.base.model.request.RestPasswordRequest;
import com.hb0730.zoom.sys.biz.base.util.TokenUtil;
import com.hb0730.zoom.sys.biz.system.mapper.SysUserSocialMapper;
import com.hb0730.zoom.sys.biz.system.model.dto.RestPasswordDTO;
import com.hb0730.zoom.sys.biz.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthToken;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 授权用户
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/9
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserService {
    private final SysUserService userService;
    private final SysUserSocialMapper userSocialMapper;
    @Autowired(required = false)
    private CaptchaService captchaService;

    /**
     * 重置密码
     *
     * @param token   token
     * @param request 请求
     * @return 是否成功
     */
    public R<String> restPassword(String token, RestPasswordRequest request) {
        Pair<String, Long> parseToken = TokenUtil.parseToken(token);
        if (null == parseToken) {
            return R.NG("非法token");
        }
        String userId = parseToken.getCode();
        // 重置密码
        RestPasswordDTO dto = new RestPasswordDTO();
        dto.setUserId(userId);
        dto.setOldPassword(request.getOldPassword());
        dto.setNewPassword(request.getNewPassword());
        return userService.resetPassword(dto);
    }

    /**
     * 社交注册
     *
     * @param authUserData 授权用户
     * @param userInfo     用户信息
     */
    public void socialRegister(AuthUser authUserData, UserInfo userInfo) {
        String authId = authUserData.getSource().toLowerCase() + authUserData.getUuid();

        SysUserSocial userSocial = new SysUserSocial();
        // 设置用户信息
        userSocial.setUserId(userInfo.getId());
        // 设置AuthId
        userSocial.setAuthId(authId);
        // 设置来源
        userSocial.setSource(authUserData.getSource().toLowerCase());
        // 设置accessToken
        AuthToken token = authUserData.getToken();
        // 设置accessToken
        userSocial.setAccessToken(token.getAccessToken());
        // 设置过期时间
        userSocial.setExpireIn(token.getExpireIn());
        // 设置刷新token
        userSocial.setRefreshToken(token.getRefreshToken());
        // 设置openId
        userSocial.setOpenId(authUserData.getUuid());
        // 设置用户名
        userSocial.setUserName(authUserData.getUsername());
        // 设置昵称
        userSocial.setNickName(authUserData.getNickname());
        // 设置邮箱
        userSocial.setEmail(authUserData.getEmail());
        // 设置头像
        userSocial.setAvatar(authUserData.getAvatar());
        // 设置accessCode
        userSocial.setAccessCode(token.getAccessCode());
        // 设置unionId
        userSocial.setUnionId(token.getUnionId());
        // 设置scope
        userSocial.setScope(token.getScope());
        // 设置tokenType
        userSocial.setTokenType(token.getTokenType());
        // 设置idToken
        userSocial.setIdToken(token.getIdToken());
        // 设置macAlgorithm
        userSocial.setMacAlgorithm(token.getMacAlgorithm());
        // 设置macKey
        userSocial.setMacKey(token.getMacKey());
        // 设置code
        userSocial.setCode(token.getCode());
        // 设置oauthToken
        userSocial.setOauthToken(token.getOauthToken());
        // 设置oauthTokenSecret
        userSocial.setOauthTokenSecret(token.getOauthTokenSecret());

        // 是否存在
        boolean presented = userSocialMapper.of(Wrappers.lambdaQuery(SysUserSocial.class)
                        .eq(SysUserSocial::getAuthId, authId))
                .present();
        if (presented) {
            throw new ZoomException("此三方账号已经绑定过");
        }
        // 查询是否已经绑定用户
        List<SysUserSocial> userSocials = userSocialMapper.of(
                Wrappers.lambdaQuery(SysUserSocial.class)
                        .eq(SysUserSocial::getUserId, userInfo.getId())
                        .eq(SysUserSocial::getSource, authUserData.getSource())
        ).list();
        if (CollectionUtil.isEmpty(userSocials)) {
            // 没有绑定用户, 新增用户信息
            userSocialMapper.insert(userSocial);
            return;
        }
        throw new ZoomException("此三方账号已经绑定过");
    }


    /**
     * 获取绑定的社交账号
     *
     * @param userId 用户id
     * @return 社交账号
     */
    public List<String> getBindSocials(String userId) {
        List<SysUserSocial> userSocials = userSocialMapper.of(
                Wrappers.lambdaQuery(SysUserSocial.class)
                        .eq(SysUserSocial::getUserId, userId)
        ).list();
        if (CollectionUtil.isEmpty(userSocials)) {
            return null;
        }
        return userSocials.stream().map(SysUserSocial::getSource).map(String::toLowerCase).toList();
    }

    /**
     * 社交解绑
     *
     * @param userId 用户id
     * @param source 来源
     */
    @Transactional(rollbackFor = Exception.class)
    public void socialUnbind(String userId, String source) {
        List<SysUserSocial> userSocials = userSocialMapper.of(
                Wrappers.lambdaQuery(SysUserSocial.class)
                        .eq(SysUserSocial::getUserId, userId)
                        .eq(SysUserSocial::getSource, source)
        ).list();
        if (CollectionUtil.isEmpty(userSocials)) {
            throw new ZoomException("未绑定此三方账号");
        }
        userSocialMapper.of().remove(
                Wrappers.lambdaQuery(SysUserSocial.class)
                        .eq(SysUserSocial::getUserId, userId)
                        .eq(SysUserSocial::getSource, source)
        );
    }

    /**
     * 重置邮箱
     *
     * @param request 请求
     * @return 是否成功
     */
    public R<String> resetEmail(RestEmailOrPhoneRequest request) {
        R<String> res = captchaService.checkCaptcha(request.getKey(), request.getCaptchaCode());
        if (!res.isSuccess()) {
            return res;
        }
        //判断邮箱是否存在
        if (userService.existEmail(request.getKey())) {
            return R.NG("邮箱已经存在");
        }
        // 更新邮箱
        userService.updateEmail(request.getOperatorId(), request.getKey());
        return R.OK();
    }

    /**
     * 重置手机号
     *
     * @param request 请求
     * @return 是否成功
     */
    public R<String> resetPhone(RestEmailOrPhoneRequest request) {
        R<String> res = captchaService.checkCaptcha(request.getKey(), request.getCaptchaCode());
        if (!res.isSuccess()) {
            return res;
        }
        //判断手机号是否存在
        if (userService.existPhone(request.getKey())) {
            return R.NG("手机号已经存在");
        }
        // 更新手机号
        userService.updatePhone(request.getOperatorId(), request.getKey());
        return R.OK();
    }
}
