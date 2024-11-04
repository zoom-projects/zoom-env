package com.hb0730.zoom.sys.biz.system.model.vo;

import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@Data
@EqualsAndHashCode
@Schema(description = "开放接口")
public class SysOpenApiVO implements Domain {
    @Schema(description = "主键")
    private String id;
    /**
     * 接口标识
     */
    @Schema(description = "接口标识")
    private String apiCode;
    /**
     * 接口名称
     */
    @Schema(description = "接口名称")
    private String apiName;
    /**
     * 所属模块
     */
    @Schema(description = "所属模块")
    private String module;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Boolean status;
}
