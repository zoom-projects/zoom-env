package com.hb0730.zoom.sys.biz.base.convert;

import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.sys.biz.base.model.vo.UserSubscribeMsgVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeGroupVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserSubscribeMsgConvert extends BaseMapstruct<UserSubscribeMsgVO, SysMessageSubscribeGroupVO> {
}
