package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessToken;
import com.hb0730.zoom.base.utils.RandomUtil;
import com.hb0730.zoom.sys.biz.base.util.TokenUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysUserAccessTokenConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysUserAccessTokenMapper;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysUserAccessTokenVO;
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
public class SysUserAccessTokenService extends SuperServiceImpl<String, SysUserAccessTokenQueryRequest,
        SysUserAccessTokenVO, SysUserAccessToken, SysUserAccessTokenCreateRequest, SysUserAccessTokenCreateRequest,
        SysUserAccessTokenMapper, SysUserAccessTokenConvert> {
    private final SysUserAccessTokenOpenApiService sysUserAccessTokenOpenApiService;

    /**
     * 通过访问令牌ID获取开放接口ID
     *
     * @param accessTokenId 访问令牌ID
     * @return 开放接口ID
     */
    public List<String> getOpenApiIds(String accessTokenId) {
        return sysUserAccessTokenOpenApiService.getOpenApiIds(accessTokenId);
    }

    /**
     * 创建访问令牌
     *
     * @param request 请求
     * @return 访问令牌
     */
    @Transactional(rollbackFor = Exception.class)
    public String createAccessToken(String userId, SysUserAccessTokenCreateRequest request) {
        SysUserAccessToken reqToEntity = mapstruct.createReqToEntity(request);
        reqToEntity.setUserId(userId);
        //创建accessToken
        long expireTime = -1;
        if (request.getExpireTime() != null) {
            expireTime = request.getExpireTime().getTime();
        }
        //生成访问令牌 用户ID+随机数+name
        String prefix = userId + RandomUtil.randomString(6) + request.getName();
        String accessToken = TokenUtil.generateToken(prefix, expireTime);
        reqToEntity.setAccessToken(accessToken);
        save(reqToEntity);
        // 保存开放接口
        sysUserAccessTokenOpenApiService.save(reqToEntity, request.getOpenApiIds());
        return accessToken;
    }

    /**
     * 取消访问令牌
     *
     * @param id 访问令牌
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelAccessToken(String id) {
        LambdaUpdateWrapper<SysUserAccessToken> updateWrapper = Wrappers.lambdaUpdate(SysUserAccessToken.class)
                .eq(SysUserAccessToken::getId, id)
                .set(SysUserAccessToken::getStatus, false);
        return update(updateWrapper);
    }

    /**
     * 恢复访问令牌
     *
     * @param id 访问令牌
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean resumeAccessToken(String id) {
        LambdaUpdateWrapper<SysUserAccessToken> updateWrapper = Wrappers.lambdaUpdate(SysUserAccessToken.class)
                .eq(SysUserAccessToken::getId, id)
                .set(SysUserAccessToken::getStatus, true);
        return update(updateWrapper);
    }

    /**
     * 通过访问令牌删除
     *
     * @param id 访问令牌
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(String id) {
        sysUserAccessTokenOpenApiService.removeByAccessTokenId(id);
        return removeById(id);
    }
}
