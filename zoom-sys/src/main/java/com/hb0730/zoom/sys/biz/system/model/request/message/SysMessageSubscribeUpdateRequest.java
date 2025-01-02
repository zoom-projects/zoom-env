package com.hb0730.zoom.sys.biz.system.model.request.message;

import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@Schema(name = "消息订阅更新")
public class SysMessageSubscribeUpdateRequest implements Domain {
    @Schema(description = "归属模块")
    @NotBlank(message = "归属模块不能为空")
    private String module;
    @Schema(description = "标识")
    @NotBlank(message = "标识不能为空")
    private String code;
    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;
    @Schema(description = "描述")
    private String desc;
}
