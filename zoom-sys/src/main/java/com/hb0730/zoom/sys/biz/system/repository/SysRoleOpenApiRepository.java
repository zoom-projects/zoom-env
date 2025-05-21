package com.hb0730.zoom.sys.biz.system.repository;

import com.hb0730.zoom.base.core.repository.BasicRepository;
import com.hb0730.zoom.base.sys.system.entity.SysRoleOpenApi;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysRoleOpenApiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysRoleOpenApiRepository extends BasicRepository<String, SysRoleOpenApi, SysRoleOpenApiMapper> {

    /**
     * 根据角色ID查询
     *
     * @param roleId 角色ID
     * @return 列表
     */
    public List<SysRoleOpenApi> listByRoleId(String roleId) {
        return lambdaQuery().eq(SysRoleOpenApi::getRoleId, roleId).list();
    }

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(String roleId) {
        return lambdaUpdate().eq(SysRoleOpenApi::getRoleId, roleId).remove();
    }

}
