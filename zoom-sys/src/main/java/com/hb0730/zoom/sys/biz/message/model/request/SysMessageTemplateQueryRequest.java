package com.hb0730.zoom.sys.biz.message.model.request;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.annotation.Like;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "消息模板查询")
public class SysMessageTemplateQueryRequest extends PageRequest {
    @Schema(description = "模板类型")
    @Equals
    private String templateType;
    @Schema(description = "模板CODE")
    @Equals
    private String templateCode;
    @Schema(description = "模板名称")
    @Like
    private String templateName;
    @Schema(description = "状态")
    @Equals
    private Boolean status;

}
