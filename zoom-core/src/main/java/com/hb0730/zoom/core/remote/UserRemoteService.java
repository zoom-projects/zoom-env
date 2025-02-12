package com.hb0730.zoom.core.remote;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.UserDTO;
import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.base.ext.services.remote.SysUserRpcService;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.sys.system.entity.SysUserSettings;
import com.hb0730.zoom.base.sys.system.service.UserAccessTokenService;
import com.hb0730.zoom.base.sys.system.service.UserService;
import com.hb0730.zoom.base.sys.system.service.UserSettingsService;
import com.hb0730.zoom.base.sys.system.service.UserSubscribeMsgService;
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
    private UserAccessTokenService userAccessTokenService;
    @Autowired
    private UserConvert userConvert;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private UserSubscribeMsgService userSubscribeMsgService;

    @Override
    public UserInfoDTO findUsername(String username) {
        SysUser user = userService.findByUsername(username);
        if (null == user) {
            return null;
        }
        UserInfoDTO userInfo = userConvert.toObject(user);

        SysUserSettings userSettings = userSettingsService.findByUserId(user.getId());
        if (null != userSettings) {
            userInfo.setMessageNotification(userSettings.getMessageNotification());
        }
        return userInfo;
    }

    @Override
    public boolean checkOpenApiAuth(String token, String apiName) {
        return userAccessTokenService.checkOpenApiAuth(token, apiName);
    }

    @Override
    public UserDTO findUserByToken(String token) {
        String userId = userAccessTokenService.findUserIdByToken(token);
        SysUser user = userService.getById(userId);
        if (null != user) {
            return userConvert.toDTO(user);
        }
        return null;
    }

    @Override
    public boolean subscribedMsg(String username, String code, MessageTypeEnums msgType) {
        SysUser user = userService.findByUsername(username);
        if (null == user) {
            return false;
        }
        return userSubscribeMsgService.checkSubscribe(user.getId(), code, msgType);
    }
}
