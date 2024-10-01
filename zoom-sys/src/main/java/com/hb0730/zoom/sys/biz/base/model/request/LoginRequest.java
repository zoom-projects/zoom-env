package com.hb0730.zoom.sys.biz.base.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Data
@Schema(description = "登录请求")
public class LoginRequest implements Serializable {
    @NotEmpty
    @Schema(description = "用户名")
    private String username;
    @NotEmpty
    @Schema(description = "密码")
    private String password;
    /**
     * 验证码
     */
    @Schema(description = "验证码KEY")
    @Size(min = 16)
    private String captchaKey;
}
