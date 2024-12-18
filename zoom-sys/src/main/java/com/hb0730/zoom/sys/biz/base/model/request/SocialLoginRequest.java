package com.hb0730.zoom.sys.biz.base.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * 社交登录
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/18
 */
@Data
@EqualsAndHashCode
@Schema(description = "社交登录")
public class SocialLoginRequest implements Serializable {
    @Schema(description = "社交登录code")
    private String code;
    @Schema(description = "社交登录state")
    private String state;
    @Schema(description = "社交登录类型")
    private String source;

    /**
     * 转换
     *
     * @param map map
     * @return SocialLoginRequest
     */
    public static SocialLoginRequest of(Map<String, String> map) {
        SocialLoginRequest request = new SocialLoginRequest();
        request.setCode(map.get("socialCode"));
        request.setState(map.get("socialState"));
        request.setSource(map.get("socialSource"));
        return request;
    }
}
