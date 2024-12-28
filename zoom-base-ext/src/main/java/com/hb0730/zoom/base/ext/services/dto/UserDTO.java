package com.hb0730.zoom.base.ext.services.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户信息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/6
 */
@Data
@EqualsAndHashCode
public class UserDTO {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
}
