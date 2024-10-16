package com.hb0730.zoom.sys.biz.message.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.notifty.entity.SysMessageTemplate;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageTemplateVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysMessageTemplateConvert extends BizMapstruct<SysMessageTemplateVO, SysMessageTemplate,
        SysMessageTemplateCreateRequest, SysMessageTemplateUpdateRequest> {
}
