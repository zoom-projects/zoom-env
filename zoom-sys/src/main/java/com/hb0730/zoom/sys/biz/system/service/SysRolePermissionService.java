package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysRolePermission;
import com.hb0730.zoom.sys.biz.system.mapper.SysRolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@Service
public class SysRolePermissionService extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> {

    /**
     * 删除角色权限
     *
     * @param roleId 角色id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(String roleId) {
        LambdaUpdateWrapper<SysRolePermission> updateWrapper = Wrappers.lambdaUpdate(SysRolePermission.class)
                .eq(SysRolePermission::getRoleId, roleId);
        return remove(updateWrapper);
    }

    /**
     * 获取角色权限
     *
     * @param roleId 角色id
     * @return 权限
     */
    public List<String> getPermsByRoleId(String roleId) {
        Optional<List<SysRolePermission>> permissions = baseMapper.of(
                query -> query.eq(SysRolePermission::getRoleId, roleId)
        ).listOptional();
        return permissions
                .map(sysRolePermissions -> sysRolePermissions.stream()
                        .map(SysRolePermission::getPermissionId)
                        .toList()).orElse(List.of());

    }
}
