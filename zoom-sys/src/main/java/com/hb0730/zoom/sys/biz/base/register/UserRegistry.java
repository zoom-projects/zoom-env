package com.hb0730.zoom.sys.biz.base.register;

import com.hb0730.zoom.base.sys.system.entity.SysUser;

/**
 * 用户注册
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/2/25
 */
public interface UserRegistry {

    /**
     * 注册
     *
     * @param username 用户名
     * @return 用户
     */
    SysUser register(String username);


    /**
     * 获取注册类型
     *
     * @return 注册类型
     */
    RegistryType getType();
}
