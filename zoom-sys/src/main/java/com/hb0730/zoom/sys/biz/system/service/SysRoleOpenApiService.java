package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysRoleOpenApi;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.sys.biz.system.mapper.SysRoleOpenApiMapper;
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
public class SysRoleOpenApiService extends ServiceImpl<SysRoleOpenApiMapper, SysRoleOpenApi> {

    /**
     * 根据角色id获取开放接口id
     *
     * @param roleId 角色id
     * @return 开放接口id
     */
    public List<String> listByRoleId(String roleId) {
        return baseMapper.of().wrapper(
                Wrappers.<SysRoleOpenApi>lambdaQuery().eq(SysRoleOpenApi::getRoleId, roleId)
        ).list().stream().map(SysRoleOpenApi::getOpenApiId).toList();
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
        return saveBatch(list);
    }

    /**
     * 根据角色id删除
     *
     * @param roleId 角色id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(String roleId) {
        return baseMapper.of(
                Wrappers.<SysRoleOpenApi>lambdaQuery().eq(SysRoleOpenApi::getRoleId, roleId)
        ).remove();
    }
}
