package com.hb0730.zoom.sys.biz.system.model.vo;

import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@Schema(name = "消息订阅")
public class SysMessageSubscribeVO implements Domain {
    @Schema(description = "id")
    private String id;
    @Schema(description = "归属模块")
    private String module;
    @Schema(description = "标识")
    private String code;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "描述")
    private String desc;
}
