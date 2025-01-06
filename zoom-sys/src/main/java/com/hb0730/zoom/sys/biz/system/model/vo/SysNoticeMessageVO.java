package com.hb0730.zoom.sys.biz.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Data
@Schema(description = "通知消息")
public class SysNoticeMessageVO implements Domain {
    @Schema(description = "id")
    private String id;
    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "状态")
    private Integer status;
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date created;
}
