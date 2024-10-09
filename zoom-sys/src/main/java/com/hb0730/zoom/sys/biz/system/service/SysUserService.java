package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.utils.PasswdUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.core.SysConst;
import com.hb0730.zoom.sys.biz.system.convert.SysUserConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysUserMapper;
import com.hb0730.zoom.sys.biz.system.model.dto.RestPasswordDTO;
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
    private final SysUserConvert userConvert;
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
     * 重置密码
     *
     * @param dto 重置密码
     * @return 是否成功
     */
    public R<String> resetPassword(RestPasswordDTO dto) {
        SysUser user = null;
        String userId = dto.getUserId();
        if (StrUtil.isNotBlank(userId)) {
            user = getById(userId);
        }
        if (user == null && StrUtil.isNotBlank(dto.getUsername())) {
            user = findByUsername(dto.getUsername());
        }
        if (user == null) {
            return R.NG("用户不存在");
        }
        // 校验旧密码
        if (!PasswdUtil.matches(dto.getOldPassword(), user.getSalt(), user.getPassword())) {
            return R.NG("旧密码错误");
        }
        // 校验新密码
        if (!PasswdUtil.checkPwd(dto.getNewPassword())) {
            return R.NG("新密码不满足强密码要求");
        }
        // 重新生成盐
        String salt = PasswdUtil.generateSalt();
        // 生成新密码
        String password = PasswdUtil.encrypt(dto.getNewPassword(), salt);
        user.setPassword(password);
        user.setSalt(salt);
        baseMapper.updateById(user);
        // 更新密码
        return R.OK("重置密码成功");
    }

}
