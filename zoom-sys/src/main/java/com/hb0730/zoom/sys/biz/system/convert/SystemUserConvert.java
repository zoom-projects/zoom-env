package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.sys.biz.system.model.dto.SysUserDTO;
import org.mapstruct.Mapper;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/25
 */
@Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SystemUserConvert extends BaseMapstruct<SysUserDTO, SysUser> {

}
