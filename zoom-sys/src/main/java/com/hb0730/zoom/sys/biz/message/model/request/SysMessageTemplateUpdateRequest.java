package com.hb0730.zoom.sys.biz.message.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@Data
@EqualsAndHashCode
@Schema(description = "消息模板更新")
public class SysMessageTemplateUpdateRequest implements Serializable {
    @Schema(description = "模板类型")
    private String templateType;

    @Schema(description = "模板标题")
    private String templateName;

    /**
     * 模板内容{0}为占位符
     */
    @Schema(description = "模板内容{0}为占位符 text")
    @NotBlank(message = "模板内容不能为空")
    private String templateContentText;
    /**
     * 模板内容{0}为占位符
     */
    @Schema(description = "模板内容{0}为占位符 html")
    @NotBlank(message = "模板内容不能为空")
    private String templateContentHtml;
}
