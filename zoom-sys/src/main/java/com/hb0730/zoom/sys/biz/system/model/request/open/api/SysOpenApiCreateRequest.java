package com.hb0730.zoom.sys.biz.system.model.request.open.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 开放接口创建请求
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@Data
@Schema(description = "开放接口创建请求")
public class SysOpenApiCreateRequest implements Serializable {
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
    private Boolean status = true;
}
