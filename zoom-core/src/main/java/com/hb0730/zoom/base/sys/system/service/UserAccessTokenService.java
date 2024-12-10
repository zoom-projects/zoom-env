package com.hb0730.zoom.base.sys.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysOpenApi;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessToken;
import com.hb0730.zoom.base.sys.system.mapper.OpenApiMapper;
import com.hb0730.zoom.base.sys.system.mapper.UserAccessTokenMapper;
import com.hb0730.zoom.base.utils.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/4
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserAccessTokenService extends ServiceImpl<UserAccessTokenMapper, SysUserAccessToken> {
    private final OpenApiMapper openApiMapper;

    /**
     * openApi token 校验
     *
     * @param token   token
     * @param apiName api名称
     * @return 是否有效
     */
    public boolean checkOpenApiAuth(String token, String apiName) {
        SysUserAccessToken accessToken = baseMapper.findByToken(token);
        if (null == accessToken) {
            return false;
        }
        Date expireTime = accessToken.getExpireTime();
        // 是否已过期
        if (null != expireTime && expireTime.before(new Date())) {
            return false;
        }
        // 获取openApi
        List<String> openApiIds = baseMapper.getOpenApisByToken(token);
        if (null == openApiIds || openApiIds.isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<SysOpenApi> queryWrapper = Wrappers.lambdaQuery(SysOpenApi.class)
                .in(SysOpenApi::getId, openApiIds);
        List<SysOpenApi> openApis = openApiMapper.selectList(queryWrapper);
        if (CollectionUtil.isEmpty(openApis)) {
            return false;
        }
        return openApis.stream().map(SysOpenApi::getApiCode).toList().contains(apiName);
    }

    /**
     * 根据token查询用户信息
     *
     * @param token token
     * @return 用户信息
     */
    public String findUserIdByToken(String token) {
        SysUserAccessToken accessToken = baseMapper.findByToken(token);
        if (null == accessToken) {
            return null;
        }
        return accessToken.getUserId();
    }
}
