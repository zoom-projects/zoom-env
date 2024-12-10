package com.hb0730.zoom.core.convert;

import com.hb0730.zoom.base.ext.services.dto.UserDTO;
import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysUser;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/21
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserConvert extends BaseMapstruct<UserInfoDTO, SysUser> {

    /**
     * 转换
     *
     * @param user 用户
     * @return 用户信息
     */
    UserDTO toDTO(SysUser user);
}
