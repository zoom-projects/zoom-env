package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.util.Md5Util;
import com.hb0730.zoom.base.util.RandomUtil;
import com.hb0730.zoom.biz.service.ZoomBizService;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.core.SysConst;
import com.hb0730.zoom.sys.biz.system.convert.SystemUserConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysUserMapper;
import com.hb0730.zoom.sys.biz.system.mode.request.UserCreateRequest;
import com.hb0730.zoom.sys.define.cache.UserCacheKeyDefine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserService extends ZoomBizService<SysUserMapper, SysUser> {
    private final SystemUserConvert userConvert;
    private final CacheUtil cache;

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    public SysUser findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }

    /**
     * 校验用户是否有效
     *
     * @param user 用户
     * @return 是否有效
     */
    public R<?> checkUserIsEffective(SysUser user) {
        if (user == null) {
            return R.NG("该用户不存在，请注册");
        }
        // 情况2：根据用户信息查询，该用户已注销
        if (SysConst.DEL_FLAG_1.equals(user.getDelFlag())) {
            return R.NG("该用户已注销");
        }
        // 情况3：根据用户信息查询，该用户已冻结
        if (SysConst.USER_FREEZE.equals(user.getStatus())) {
            return R.NG("该用户已冻结");
        }
        return R.OK();
    }

    /**
     * 更新最后登录时间
     *
     * @param id 用户ID
     */
    public void updateLastLoginTime(String id) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setLastLoginTime(new Date());
        baseMapper.updateById(user);
    }

    /**
     * 创建用户
     *
     * @param request 用户信息
     * @return 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public String createUser(UserCreateRequest request) {
        SysUser user = userConvert.to(request);
        // 查询用户名是否存在
        if (checkUsernamePresent(user)) {
            throw new ZoomException("用户名已存在");
        }
        // 生成盐
        user.setSalt(RandomUtil.randomString(16));
        // 密码加密
        user.setPassword(Md5Util.md5Hex(user.getPassword(), user.getSalt()));
        // 插入
        this.save(user);
        // 清理缓存
        cache.del(UserCacheKeyDefine.LOGIN_FAILURE.format(user.getUsername()));
        // 返回用户ID
        return user.getId();

    }


    /**
     * 检查用户名是否存在
     *
     * @param domain 用户
     * @return 是否存在
     */
    public Boolean checkUsernamePresent(SysUser domain) {
        // 构造条件
        LambdaQueryWrapper<SysUser> wrapper = this.baseMapper.lambda()
                // 更新时忽略当前记录
                .ne(SysUser::getId, domain.getId())
                .eq(SysUser::getUsername, domain.getUsername());
        // 查询
        return this.baseMapper
                .of(wrapper)
                .present();
    }
}
