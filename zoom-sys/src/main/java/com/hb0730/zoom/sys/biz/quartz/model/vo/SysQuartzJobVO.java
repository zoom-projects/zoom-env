package com.hb0730.zoom.sys.biz.quartz.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Data
@EqualsAndHashCode
@Schema(name = "计划任务")
public class SysQuartzJobVO implements Serializable {
    @Schema(description = "ID")
    private String id;
    /**
     * 所属系统代码
     */
    @Schema(description = "所属系统代码")
    private String appName;
    /**
     * 任务类名
     */
    @Schema(description = "任务类名")
    private String jobClassName;
    /**
     * 参数
     */
    @Schema(description = "参数")
    private String parameter;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
    /**
     * cron表达式
     */
    @Schema(description = "cron表达式")
    private String cronExpression;

    /**
     * 状态 0正常 -1停止
     */
    @Schema(description = "状态 0正常 -1停止")
    private Boolean status;
}
