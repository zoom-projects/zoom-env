package com.hb0730.zoom.base.ext.services.remote;

import com.hb0730.zoom.base.ext.services.dto.UserDTO;
import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.sofa.rpc.core.annotation.RpcAppName;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/21
 */
@RpcAppName("zoom-app")
public interface SysUserRpcService {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserInfoDTO findUsername(String username);

    /**
     * 检查openApi权限
     *
     * @param token   token
     * @param apiName api名称
     * @return 是否有权限
     */
    boolean checkOpenApiAuth(String token, String apiName);

    /**
     * 根据token查询用户信息
     *
     * @param token token
     * @return 用户信息
     */
    UserDTO findUserByToken(String token);
}
