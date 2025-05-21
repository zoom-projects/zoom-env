package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.sys.system.entity.SysRolePermission;
import com.hb0730.zoom.sys.biz.system.repository.SysRolePermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@Service
@RequiredArgsConstructor
public class SysRolePermissionService {

    private final SysRolePermissionRepository repository;

    /**
     * 删除角色权限
     *
     * @param roleId 角色id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(String roleId) {
        return repository.removeByRoleId(roleId);
    }

    /**
     * 获取角色权限
     *
     * @param roleId 角色id
     * @return 权限
     */
    public List<String> getPermsByRoleId(String roleId) {
        Optional<List<SysRolePermission>> permissions = repository.listByRoleId(roleId);
        return permissions
                .map(sysRolePermissions -> sysRolePermissions.stream()
                        .map(SysRolePermission::getPermissionId)
                        .toList()).orElse(List.of());

    }

    /**
     * 获取角色权限
     *
     * @param roleIds 角色id
     * @return 权限
     */
    public List<String> getPermsByRoleIds(List<String> roleIds) {
        Optional<List<SysRolePermission>> permissions = repository.listByRoleIds(roleIds);
        return permissions
                .map(sysRolePermissions -> sysRolePermissions.stream()
                        .map(SysRolePermission::getPermissionId)
                        .toList()).orElse(List.of());

    }


    /**
     * 批量保存角色权限
     *
     * @param rolePermissions 角色权限
     * @return 是否成功
     */
    public boolean saveBatch(List<SysRolePermission> rolePermissions) {
        return repository.saveBatch(rolePermissions);
    }
}
