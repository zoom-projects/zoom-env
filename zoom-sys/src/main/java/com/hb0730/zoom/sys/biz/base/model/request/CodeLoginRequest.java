package com.hb0730.zoom.sys.biz.base.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/19
 */
@Data
@Schema(description = "验证码登录")
public class CodeLoginRequest implements Serializable {
    /**
     * 账号
     */
    @Schema(description = "账号")
    private String username;
    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String captchaCode;

    /**
     * 验证码key
     */
    @Schema(description = "验证码key")
    private String captchaKey;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    private String timestamp;


    /**
     * 转换为对象
     *
     * @param params 参数
     * @return 对象
     */
    public static CodeLoginRequest of(Map<String, String> params) {
        CodeLoginRequest request = new CodeLoginRequest();
        request.setUsername(params.get("username"));
        request.setCaptchaCode(params.get("captchaCode"));
        request.setCaptchaKey(params.get("captchaKey"));
        request.setTimestamp(params.get("timestamp"));
        return request;
    }
}
