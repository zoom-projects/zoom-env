package com.hb0730.zoom.sys.biz.system.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 开放接口组
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Data
@EqualsAndHashCode
public class SysOpenApiGroupDTO implements Serializable {
    /**
     * 模块
     */
    private String module;
    /**
     * 描述
     */
    private List<SysOpenApiChildDTO> children;

    /**
     * 接口标识
     */
    @Data
    @EqualsAndHashCode

    public static class SysOpenApiChildDTO implements Serializable {
        /**
         * 接口标识
         */
        private String apiCode;
        /**
         * 接口名称
         */
        private String apiName;
        /**
         * 描述
         */
        private String description;
        /**
         * 状态
         */
        private Boolean status = true;
    }
}
