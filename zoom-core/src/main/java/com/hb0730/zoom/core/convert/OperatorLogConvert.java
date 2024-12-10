package com.hb0730.zoom.core.convert;

import com.hb0730.zoom.base.ext.services.dto.SaveOperatorLogDTO;
import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysOperatorLog;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/6
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface OperatorLogConvert extends BaseMapstruct<SaveOperatorLogDTO, SysOperatorLog> {
}
