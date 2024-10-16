package com.hb0730.zoom.sys.biz.message.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@Data
@EqualsAndHashCode
@Schema(description = "消息模板")
public class SysMessageTemplateVO implements Serializable {

    @Schema(description = "ID")
    private String id;
    /**
     * 模板类型
     * <p>
     * 1-短信
     * 2-邮件
     */
    @Schema(description = "模板类型")
    private String templateType;

    /**
     * 模板CODE
     */
    @Schema(description = "模板CODE")
    private String templateCode;

    /**
     * 模板标题
     */
    @Schema(description = "模板标题")
    private String templateName;

    /**
     * 模板内容{0}为占位符
     */
    @Schema(description = "模板内容{0}为占位符 text")
    private String templateContentText;
    /**
     * 模板内容{0}为占位符
     */
    @Schema(description = "模板内容{0}为占位符 html")
    private String templateContentHtml;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "修改人")
    private String modifiedBy;
}
