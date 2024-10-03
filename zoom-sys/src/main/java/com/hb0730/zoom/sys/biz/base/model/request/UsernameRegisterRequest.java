package com.hb0730.zoom.sys.biz.base.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 用户名注册
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/2
 */
@Data
@Schema(description = "用户名注册")
public class UsernameRegisterRequest implements Serializable {
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码")
    private String password;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 验证码KEY
     */
    @Schema(description = "验证码KEY")
    @NotBlank(message = "验证码KEY不能为空")
    private String captchaKey;
    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    @NotBlank(message = "时间戳不能为空")
    private String timestamp;


    /**
     * 转换
     *
     * @param map map
     * @return {@link UsernameRegisterRequest}
     */
    public static UsernameRegisterRequest of(Map<String, String> map) {
        UsernameRegisterRequest register = new UsernameRegisterRequest();
        register.setUsername(map.get("username"));
        register.setPassword(map.get("password"));
        register.setPhone(map.get("phone"));
        register.setCode(map.get("code"));
        register.setCaptchaKey(map.get("captchaKey"));
        register.setTimestamp(map.get("timestamp"));
        return register;
    }
}
