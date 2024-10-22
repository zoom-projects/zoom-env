package com.hb0730.zoom.core.remote;

import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.base.ext.services.remote.SysUserRpcService;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.sys.system.service.UserService;
import com.hb0730.zoom.core.convert.UserConvert;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/21
 */
@RemoteService
@Slf4j
public class UserRemoteService implements SysUserRpcService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConvert userConvert;

    @Override
    public UserInfoDTO findUsername(String username) {
        SysUser user = userService.findByUsername(username);
        if (null == user) {
            return null;
        }
        return userConvert.toObject(user);
    }
}
