package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysRole;
import com.hb0730.zoom.base.sys.system.entity.SysRolePermission;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysRoleConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysRoleMapper;
import com.hb0730.zoom.sys.biz.system.model.request.role.SysRoleCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.role.SysRoleQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.request.role.SysRoleUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysRoleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleService extends SuperServiceImpl<String, SysRoleQueryRequest, SysRoleVO, SysRole,
        SysRoleCreateRequest, SysRoleUpdateRequest, SysRoleMapper, SysRoleConvert> {
    private final SysRolePermissionService sysRolePermissionService;
    private final SysRoleOpenApiService sysRoleOpenApiService;


    /**
     * 是否存在
     *
     * @param id   id
     * @param code code
     * @return 是否存在
     */
    public R<String> hasCode(String id, String code) {
        boolean present = baseMapper.of(
                query -> query.eq(SysRole::getRoleCode, code)
                        .ne(SysRole::getId, id)
        ).present();
        return present ? R.NG("角色标识已存在") : R.OK();
    }

    @Override
    public boolean create(SysRoleCreateRequest req) {
        R<String> jr = hasCode(null, req.getRoleCode());
        if (!jr.isSuccess()) {
            throw new ZoomException(jr.getMessage());
        }
        return super.create(req);
    }

    @Override
    public boolean updateById(String id, SysRoleUpdateRequest req) {
        R<String> jr = hasCode(id, req.getRoleCode());
        if (!jr.isSuccess()) {
            throw new ZoomException(jr.getMessage());
        }
        return super.updateById(id, req);
    }


    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String id) {
        SysRole entity = getById(id);
        if (null == entity) {
            throw new ZoomException("角色不存在");
        }
        return removeById(id);
    }


    /**
     * 获取权限
     *
     * @param id 角色id
     * @return 权限
     */
    public List<String> getPerms(String id) {
        return sysRolePermissionService.getPermsByRoleId(id);
    }

    /**
     * 赋权
     *
     * @param id            id
     * @param permissionIds 权限id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean grant(String id, List<String> permissionIds) {
        SysRole entity = getById(id);
        if (null == entity) {
            throw new ZoomException("角色不存在");
        }
        // 删除旧的权限
        sysRolePermissionService.removeByRoleId(id);
        // 新增权限
        if (CollectionUtil.isNotEmpty(permissionIds)) {
            List<SysRolePermission> list = permissionIds.stream().map(permissionId -> {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(id);
                sysRolePermission.setPermissionId(permissionId);
                return sysRolePermission;
            }).toList();
            sysRolePermissionService.saveBatch(list);
        }

        // 更新缓存

        return true;
    }

    /**
     * 获取开放接口
     *
     * @param id 角色id
     * @return 权限
     */
    public List<String> getOpenApis(String id) {
        return sysRoleOpenApiService.listByRoleId(id);
    }

    /**
     * 赋权
     *
     * @param id         角色id
     * @param openApiIds 权限id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean grantOpenApi(String id, List<String> openApiIds) {
        SysRole entity = getById(id);
        if (null == entity) {
            throw new ZoomException("角色不存在");
        }
        sysRoleOpenApiService.save(id, openApiIds);
        return true;
    }
}
