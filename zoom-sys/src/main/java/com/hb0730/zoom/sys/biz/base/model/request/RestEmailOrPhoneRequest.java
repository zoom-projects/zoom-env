package com.hb0730.zoom.sys.biz.base.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 邮箱或者手机号重置
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/24
 */
@Data
@Schema(description = "邮箱或者手机号重置")
public class RestEmailOrPhoneRequest implements Serializable {
    /**
     * 邮箱或者手机号
     */
    @Schema(description = "邮箱或者手机号")
    @NotBlank(message = "邮箱或者手机号不能为空")
    private String key;
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
     * 操作人id
     */
    @Schema(description = "操作人id", hidden = true)
    @JsonIgnore
    private String operatorId;
}
