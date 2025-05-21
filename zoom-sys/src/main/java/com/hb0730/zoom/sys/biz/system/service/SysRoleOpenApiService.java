package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.core.service.BasicService;
import com.hb0730.zoom.base.sys.system.entity.SysRoleOpenApi;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.sys.biz.system.repository.SysRoleOpenApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysRoleOpenApiService extends BasicService<String, SysRoleOpenApi, SysRoleOpenApiRepository> {

    /**
     * 根据角色id获取开放接口id
     *
     * @param roleId 角色id
     * @return 开放接口id
     */
    public List<String> listByRoleId(String roleId) {
        return repository.listByRoleId(roleId).stream().map(SysRoleOpenApi::getOpenApiId).toList();
    }


    /**
     * 保存角色开放接口
     *
     * @param roleId     角色id
     * @param openApiIds 开放接口id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean save(String roleId, List<String> openApiIds) {
        // 删除
        removeByRoleId(roleId);
        if (CollectionUtil.isEmpty(openApiIds)) {
            return true;
        }
        // 保存
        List<SysRoleOpenApi> list = openApiIds.stream().map(openApiId -> new SysRoleOpenApi().setRoleId(roleId).setOpenApiId(openApiId)).toList();
        return repository.saveBatch(list);
    }

    /**
     * 根据角色id删除
     *
     * @param roleId 角色id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(String roleId) {
        return repository.removeByRoleId(roleId);
    }
}
