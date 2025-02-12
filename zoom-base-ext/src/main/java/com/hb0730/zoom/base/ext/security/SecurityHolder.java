package com.hb0730.zoom.base.ext.security;

import com.hb0730.zoom.base.meta.UserInfo;

import java.util.Optional;

/**
 * SecurityUtils 的 bean 对象
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
public interface SecurityHolder {
    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    Optional<UserInfo> getLoginUser();

    /**
     * 获取当前用户id
     *
     * @return id
     */
    Optional<String> getLoginUserId();
}
