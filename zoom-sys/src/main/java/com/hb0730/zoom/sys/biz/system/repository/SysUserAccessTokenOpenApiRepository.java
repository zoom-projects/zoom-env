package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.baomidou.mybatisplus.extension.repository.IRepository;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessTokenOpenApi;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysUserAccessTokenOpenApiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysUserAccessTokenOpenApiRepository extends CrudRepository<SysUserAccessTokenOpenApiMapper, SysUserAccessTokenOpenApi> {

    /**
     * 根据accessToken查询
     *
     * @param accessTokenId accessTokenId
     * @return 列表
     */
    public List<SysUserAccessTokenOpenApi> listByAccessTokenId(String accessTokenId) {
        return lambdaQuery().eq(SysUserAccessTokenOpenApi::getAccessTokenId, accessTokenId).list();
    }


    /**
     * 批量保存
     *
     * @param list 列表
     * @return 是否成功
     */
    public boolean saveBatch(List<SysUserAccessTokenOpenApi> list) {
        return saveBatch(list, IRepository.DEFAULT_BATCH_SIZE);
    }

    /**
     * 通过访问令牌ID删除
     *
     * @param accessTokenId 访问令牌ID
     * @return 是否成功
     */
    public boolean deleteByAccessTokenId(String accessTokenId) {
        return lambdaUpdate().eq(SysUserAccessTokenOpenApi::getAccessTokenId, accessTokenId).remove();
    }
}
