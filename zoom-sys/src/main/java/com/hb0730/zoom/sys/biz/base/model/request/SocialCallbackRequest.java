package com.hb0730.zoom.sys.biz.base.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 社交绑定
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/18
 */
@Data
@EqualsAndHashCode
@Schema(description = "社交绑定")
@ToString
public class SocialCallbackRequest implements Serializable {
    /**
     * 社交登录code
     */
    @Schema(description = "社交登录code")
    private String code;
    /**
     * 社交登录state
     */
    @Schema(description = "社交登录state")
    private String state;
    /**
     * 社交登录类型
     */
    @Schema(description = "社交登录类型")
    private String source;
}
