package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysNoticeMessageVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysNoticeMessageConvert extends BizMapstruct<SysNoticeMessageVO, SysNoticeMessage,
        SysNoticeMessageCreateRequest, SysNoticeMessageCreateRequest> {
}
