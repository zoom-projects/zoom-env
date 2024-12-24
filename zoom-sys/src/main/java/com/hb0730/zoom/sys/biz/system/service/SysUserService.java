package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.sys.system.entity.SysUserRole;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.DigestUtil;
import com.hb0730.zoom.base.utils.PasswdUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysUserConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysUserMapper;
import com.hb0730.zoom.sys.biz.system.model.dto.RestPasswordDTO;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserRoleUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysUserRoleVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserService extends SuperServiceImpl<String, SysUserQueryRequest, SysUserVO, SysUser,
        SysUserCreateRequest, SysUserUpdateRequest, SysUserMapper, SysUserConvert> {
    private final SysUserConvert userConvert;
    private final CacheUtil cache;
    private final SysUserRoleService userRoleService;


    /**
     * 邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */
    public boolean existEmail(String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getEmail, email);
        return baseMapper.of(queryWrapper).present();
    }

    /**
     * 手机号是否存在
     *
     * @param phone 手机号
     * @return 是否存在
     */
    public boolean existPhone(String phone) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getPhone, phone);
        return baseMapper.of(queryWrapper).present();
    }

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
     * 更新邮箱
     *
     * @param id    用户ID
     * @param email 邮箱
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String id, String email) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setEmail(email);
        baseMapper.updateById(user);
    }

    /**
     * 更新手机号
     *
     * @param id    用户ID
     * @param phone 手机号
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePhone(String id, String phone) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setPhone(phone);
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

    /**
     * 重置密码
     *
     * @param id       用户ID
     * @param password 密码
     * @return 是否成功
     */
    public R<String> resetPassword(String id, String password) {
        SysUser user = getById(id);
        if (user == null) {
            return R.NG("用户不存在");
        }
        boolean checkPwd = PasswdUtil.checkPwd(password);
        if (!checkPwd) {
            return R.NG("新密码不满足强密码要求");
        }
        // 重新生成盐
        String salt = PasswdUtil.generateSalt();
        // 生成新密码
        String newPassword = PasswdUtil.encrypt(password, salt);
        user.setPassword(newPassword);
        user.setSalt(salt);
        baseMapper.updateById(user);
        // 更新密码
        return R.OK("重置密码成功");
    }

    /**
     * 校验是否存在
     *
     * @param type  类型
     * @param value 值
     * @param id    id
     * @return 是否存在
     */
    public R<String> hasExists(String type, String value, String id) {
        return switch (type) {
            case "user_name" -> hasExistUsername(value, id);
            case "phone" -> hasExistHashPhone(value, id);
            case "email" -> hasExistHashEmail(value, id);
            default -> R.NG("校验类型错误");
        };
    }

    /**
     * 校验用户名是否存在
     *
     * @param username 用户名
     * @param id       id
     * @return 是否存在
     */
    public R<String> hasExistUsername(String username, String id) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getUsername, username);
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.ne(SysUser::getId, id);
        }
        boolean present = baseMapper.of(queryWrapper).present();
        return present ? R.NG("用户名已存在") : R.OK("用户名可用");
    }

    /**
     * 校验手机号是否存在
     *
     * @param phone 手机号
     * @param id    id
     * @return 是否存在
     */
    public R<String> hasExistHashPhone(String phone, String id) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getHashPhone, phone);
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.ne(SysUser::getId, id);
        }
        boolean present = baseMapper.of(queryWrapper).present();
        return present ? R.NG("手机号已存在") : R.OK("手机号可用");
    }

    /**
     * 校验邮箱是否存在
     *
     * @param email 邮箱
     * @param id    id
     * @return 是否存在
     */
    public R<String> hasExistHashEmail(String email, String id) {
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery(SysUser.class)
                .eq(SysUser::getHashEmail, email);
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.ne(SysUser::getId, id);
        }
        boolean present = baseMapper.of(queryWrapper).present();
        return present ? R.NG("邮箱已存在") : R.OK("邮箱可用");
    }

    @Override
    public boolean create(SysUserCreateRequest sysUserCreateRequest) {
        //校验用户名是否存在
        R<String> r = hasExistUsername(sysUserCreateRequest.getUsername(), null);
        if (!r.isSuccess()) {
            throw new ZoomException(r.getMessage());
        }

        // 重新计算hash256Hex
        if (StrUtil.isNotBlank(sysUserCreateRequest.getPhone())) {
            String phoneHex = DigestUtil.sha256Hex(sysUserCreateRequest.getPhone());
            sysUserCreateRequest.setHashPhone(phoneHex);
            //校验手机号是否存在
            r = hasExistHashPhone(sysUserCreateRequest.getHashPhone(), null);
            if (!r.isSuccess()) {
                throw new ZoomException(r.getMessage());
            }
        } else {
            sysUserCreateRequest.setHashPhone(null);
            sysUserCreateRequest.setPhone(null);
        }
        if (StrUtil.isNotBlank(sysUserCreateRequest.getEmail())) {
            String emailHex = DigestUtil.sha256Hex(sysUserCreateRequest.getEmail());
            sysUserCreateRequest.setHashEmail(emailHex);

            //校验邮箱是否存在
            r = hasExistHashEmail(sysUserCreateRequest.getHashEmail(), null);
            if (!r.isSuccess()) {
                throw new ZoomException(r.getMessage());
            }

        } else {
            sysUserCreateRequest.setHashEmail(null);
            sysUserCreateRequest.setEmail(null);
        }
        //密码
        boolean checkPwd = PasswdUtil.checkPwd(sysUserCreateRequest.getPassword());
        if (!checkPwd) {
            throw new ZoomException("密码不满足强密码要求");
        }
        String salt = PasswdUtil.generateSalt();
        String password = PasswdUtil.encrypt(sysUserCreateRequest.getPassword(), salt);
        sysUserCreateRequest.setPassword(password);
        sysUserCreateRequest.setSalt(salt);

        return super.create(sysUserCreateRequest);
    }

    @Override
    public boolean updateById(String id, SysUserUpdateRequest request) {
        SysUser user = getById(id);
        if (user == null) {
            return false;
        }
        // 重新计算hash256Hex
        if (StrUtil.isNotBlank(request.getPhone())) {
            String phoneHex = DigestUtil.sha256Hex(request.getPhone());
            request.setHashPhone(phoneHex);
        } else {
            request.setHashPhone(null);
            request.setPhone(null);
        }
        if (StrUtil.isNotBlank(request.getEmail())) {
            String emailHex = DigestUtil.sha256Hex(request.getEmail());
            request.setHashEmail(emailHex);
        } else {
            request.setHashEmail(null);
            request.setEmail(null);
        }
        user = userConvert.updateEntity(request, user);
        return updateById(user);
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    public boolean deleteById(String id) {
        SysUser user = getById(id);
        if (user == null) {
            return false;
        }
        //TODO 禁用相关

        return removeById(id);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色
     */
    public List<SysUserRoleVO> findRoles(String userId) {
        return baseMapper.findRoles(null, userId);
    }


    /**
     * 保存用户角色
     *
     * @param id        用户ID
     * @param userRoles 用户角色
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveRoles(String id, List<SysUserRoleUpdateRequest> userRoles) {
        userRoleService.deleteByUserId(id);
        if (CollectionUtil.isEmpty(userRoles)) {
            return true;
        }
        List<SysUserRole> roles = new ArrayList<>();
        for (SysUserRoleUpdateRequest userRole : userRoles) {
            SysUserRole role = new SysUserRole();
            role.setUserId(id);
            role.setRoleId(userRole.getRoleId());
            role.setEndTime(userRole.getEndTime());
            roles.add(role);
        }
        return userRoleService.saveBatch(roles);
    }

    /**
     * 获取有效的角色
     *
     * @param userId 用户ID
     * @return 角色
     */
    public List<SysUserRole> findEffectiveRoles(String userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery(SysUserRole.class)
                .eq(SysUserRole::getUserId, userId)
                .and(wrapper -> wrapper.isNull(SysUserRole::getEndTime)
                        .or()
                        .ge(SysUserRole::getEndTime, new Date()));
        return userRoleService.list(queryWrapper);

    }
}
