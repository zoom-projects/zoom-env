package com.hb0730.zoom.sys.biz.system.repository;

import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessToken;
import com.hb0730.zoom.sys.biz.system.convert.SysUserAccessTokenConvert;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysUserAccessTokenVO;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysUserAccessTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysUserAccessTokenRepository extends BaseRepository<String, SysUserAccessTokenQueryRequest,
        SysUserAccessTokenVO, SysUserAccessToken, SysUserAccessTokenCreateRequest, SysUserAccessTokenCreateRequest,
        SysUserAccessTokenMapper, SysUserAccessTokenConvert> {

    /**
     * 根据ID更新状态
     *
     * @param status 状态
     * @param id     ID
     * @return 是否成功
     */
    public boolean updateStatusById(Boolean status, String id) {
        return lambdaUpdate()
                .eq(SysUserAccessToken::getId, id)
                .set(SysUserAccessToken::getStatus, status)
                .update();
    }
}
