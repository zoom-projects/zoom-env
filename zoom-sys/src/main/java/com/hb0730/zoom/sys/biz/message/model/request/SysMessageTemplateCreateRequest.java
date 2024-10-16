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
@Schema(description = "消息模板创建")
public class SysMessageTemplateCreateRequest implements Serializable {
    /**
     * 模板类型
     * <p>
     * 1-短信
     * 2-邮件
     */
    @Schema(description = "模板类型")
    @NotBlank(message = "模板类型不能为空")
    private String templateType;

    /**
     * 模板CODE
     */
    @Schema(description = "模板CODE")
    @NotBlank(message = "模板CODE不能为空")
    private String templateCode;

    /**
     * 模板标题
     */
    @Schema(description = "模板标题")
    @NotBlank(message = "模板标题不能为空")
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
