package com.hb0730.zoom.sys.biz.base.convert;

import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysUserSettings;
import com.hb0730.zoom.sys.biz.base.model.vo.UserSettingsVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/11
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserSettingsConvert extends BaseMapstruct<UserSettingsVO, SysUserSettings> {
}
