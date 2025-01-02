package com.hb0730.zoom.sys.biz.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@Schema(description = "消息订阅组")
public class SysMessageSubscribeGroupVO implements Serializable {
    @Schema(description = "模块")
    private String module;

    @Schema(description = "子集")
    private List<SysMessageSubscribeChildVO> children;

    /**
     * 模块
     */
    @Schema(description = "模块")
    @Data
    public static class SysMessageSubscribeChildVO implements Serializable {
        @Schema(description = "主键")
        private String id;
        /**
         * 标识
         */
        @Schema(description = "标识")
        private String code;
        /**
         * 名称
         */
        @Schema(description = "名称")
        private String name;
        /**
         * 描述
         */
        @Schema(description = "描述")
        private String desc;
    }
}
