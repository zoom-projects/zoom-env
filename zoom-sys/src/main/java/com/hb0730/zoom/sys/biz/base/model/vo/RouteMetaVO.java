package com.hb0730.zoom.sys.biz.base.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/4
 */
@Data
@EqualsAndHashCode
@ToString
@Schema(description = "路由元数据")
public class RouteMetaVO implements Serializable {
    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;
    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;
    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Boolean hidden;
    /**
     * 是否缓存
     */
    @Schema(description = "是否缓存,如果缓存，请保持route name与组件name保持一致")
    private Boolean keepAlive;
    /**
     * 是否固定
     */
    @Schema(description = "是否固定")
    private Boolean affix;
    /**
     * 是否全屏
     */
    @Schema(description = "是否全屏")
    private Boolean full;
    /**
     * iframe src
     */
    @Schema(description = "iframe src")
    private String frameSrc;

    /**
     * 是否外链
     */
    @Schema(description = "是否外链")
    private Boolean external;
}
