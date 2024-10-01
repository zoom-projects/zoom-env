package com.hb0730.zoom.sys.biz.system.mode.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/25
 */
@Data
@Schema(description = "用户创建请求")
public class UserCreateRequest implements Serializable {
    /**
     * 用户名
     */
    @NotBlank
    @Schema(description = "用户名")
    @Size(min = 6, max = 32)
    private String username;
    /**
     * 密码
     */
    @NotBlank
    @Schema(description = "密码")
    @Size(min = 6, max = 32)
    private String password;
    /**
     * 验证码
     */
    @Schema(description = "验证码KEY")
    @Size(min = 16)
    private String captchaKey;
    /**
     * 昵称
     */
    @NotBlank
    @Schema(description = "昵称")
    @Size(max = 32)
    private String nickname;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Size(max = 64)
    @Email
    private String email;
}
