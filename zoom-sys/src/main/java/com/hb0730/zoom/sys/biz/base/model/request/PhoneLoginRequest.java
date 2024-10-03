package com.hb0730.zoom.sys.biz.base.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/2
 */
@Data
@Schema(description = "手机登录")
public class PhoneLoginRequest implements Serializable {
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
    private String captchaCode;

    /**
     * 验证码key
     */
    @Schema(description = "验证码key")
    @NotBlank(message = "验证码key不能为空")
    private String captchaKey;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    @NotBlank(message = "时间戳不能为空")
    private String timestamp;

    /**
     * 转换为对象
     *
     * @param params 参数
     * @return 对象
     */
    public static PhoneLoginRequest of(Map<String, String> params) {
        PhoneLoginRequest request = new PhoneLoginRequest();
        request.setPhone(params.get("phone"));
        request.setCaptchaCode(params.get("captchaCode"));
        request.setCaptchaKey(params.get("captchaKey"));
        request.setTimestamp(params.get("timestamp"));
        return request;
    }
}
