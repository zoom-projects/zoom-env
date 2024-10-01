package com.hb0730.zoom.sys.biz.base.model.dto;

import lombok.Data;

/**
 * 登录信息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Data
public class LoginInfo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}
