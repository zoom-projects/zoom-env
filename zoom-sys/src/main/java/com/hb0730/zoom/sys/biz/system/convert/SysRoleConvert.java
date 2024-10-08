package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysRole;
import com.hb0730.zoom.sys.biz.system.model.dto.SysRoleDTO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysRoleVO;

/**
 * 角色
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysRoleConvert extends BaseMapstruct<SysRoleVO, SysRoleDTO, SysRole> {
}
