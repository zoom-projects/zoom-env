package com.hb0730.zoom.sys.biz.base.convert;

import com.hb0730.zoom.base.mapstruct.BaseDtoMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysPermission;
import com.hb0730.zoom.sys.biz.base.model.dto.PermissionDTO;
import org.mapstruct.Mapper;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PermissionCovert extends BaseDtoMapstruct<PermissionDTO, SysPermission> {
}
