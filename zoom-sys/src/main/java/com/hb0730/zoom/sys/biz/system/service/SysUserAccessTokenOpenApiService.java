package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.core.service.BasicService;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessToken;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessTokenOpenApi;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.sys.biz.system.repository.SysUserAccessTokenOpenApiRepository;
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
public class SysUserAccessTokenOpenApiService extends BasicService<String, SysUserAccessTokenOpenApi, SysUserAccessTokenOpenApiRepository> {

    /**
     * 通过访问令牌ID获取开放接口ID
     *
     * @param accessTokenId 访问令牌ID
     * @return 开放接口ID
     */
    public List<String> getOpenApiIds(String accessTokenId) {
        return repository.listByAccessTokenId(accessTokenId)
                .stream()
                .map(SysUserAccessTokenOpenApi::getOpenApiId).toList();
    }

    /**
     * 保存
     *
     * @param accessToken 访问令牌
     * @param openApiIds  开放接口ID
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysUserAccessToken accessToken, List<String> openApiIds) {
        List<SysUserAccessTokenOpenApi> list = repository.list();
        if (CollectionUtil.isEmpty(openApiIds)) {
            return false;
        }
        openApiIds.forEach(openApiId -> {
            SysUserAccessTokenOpenApi openApi = new SysUserAccessTokenOpenApi();
            openApi.setAccessTokenId(accessToken.getId());
            openApi.setAccessToken(accessToken.getAccessToken());
            openApi.setOpenApiId(openApiId);
            list.add(openApi);
        });
        return repository.saveBatch(list);
    }

    /**
     * 通过访问令牌ID删除
     *
     * @param accessTokenId 访问令牌ID
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByAccessTokenId(String accessTokenId) {
        return repository.deleteByAccessTokenId(accessTokenId);
    }
}
