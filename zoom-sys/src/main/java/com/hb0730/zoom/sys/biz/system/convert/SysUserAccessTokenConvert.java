package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysUserAccessToken;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysUserAccessTokenVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysUserAccessTokenConvert extends BizMapstruct<SysUserAccessTokenVO, SysUserAccessToken,
        SysUserAccessTokenCreateRequest, SysUserAccessTokenCreateRequest> {
}
