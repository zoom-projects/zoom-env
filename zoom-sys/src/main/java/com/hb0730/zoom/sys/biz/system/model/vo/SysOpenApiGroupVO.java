package com.hb0730.zoom.sys.biz.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Data
@EqualsAndHashCode
@Schema(description = "开放接口组")
public class SysOpenApiGroupVO implements Serializable {
    @Schema(description = "模块")
    private String module;

    @Schema(description = "子集")
    private List<SysOpenApiChildVO> children;

    /**
     * 模块
     */
    @Data
    @Schema(description = "模块")
    public static class SysOpenApiChildVO implements Serializable {
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
         * 描述
         */
        @Schema(description = "描述")
        private String description;
        /**
         * 状态
         */
        @Schema(description = "状态")
        private Boolean status = true;
    }
}
