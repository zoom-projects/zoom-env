package com.hb0730.zoom.sys.biz.message.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.message.entity.SysMessage;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysMessageConvert extends BizMapstruct<SysMessageVO, SysMessage, SysMessageCreateRequest,
        SysMessageUpdateRequest> {
}
