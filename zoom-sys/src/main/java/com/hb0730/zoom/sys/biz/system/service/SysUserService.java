package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.core.SysConst;
import com.hb0730.zoom.sys.biz.system.convert.SystemUserConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserService extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<SysUserMapper, SysUser> implements com.baomidou.mybatisplus.extension.service.IService<SysUser> {
    private final SystemUserConvert userConvert;
    private final CacheUtil cache;

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    public SysUser findByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUsername, username);
        return baseMapper.of(queryWrapper).one();
    }

    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return 用户
     */
    public SysUser findByPhone(String phone) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getPhone, phone);
        return baseMapper.of(queryWrapper).one();
    }

    /**
     * 根据邮箱查询
     *
     * @param email 邮箱
     * @return 用户
     */
    public SysUser findByEmail(String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getEmail, email);
        return baseMapper.of(queryWrapper).one();
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
