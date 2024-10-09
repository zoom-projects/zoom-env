package com.hb0730.zoom.sys.biz.base.convert;

import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.security.UserInfo;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.sys.biz.base.model.vo.UserInfoVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/9
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserInfoConvert extends BaseMapstruct<UserInfoVO, UserInfo, SysUser> {

    /**
     * do2vo
     *
     * @param userInfo 用户信息
     * @return vo
     */
    UserInfoVO do2vo(UserInfo userInfo);
}
