package com.hb0730.zoom.sys.biz.quartz.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Data
@Schema(description = "创建定时任务请求")
public class SysQuartzCreateRequest implements Serializable {
    @Schema(hidden = true)
    @JsonIgnore
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
    @NotBlank(message = "任务类名不能为空")
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
    @Schema(description = "core表达式")
    @NotBlank(message = "core表达式不能为空")
    private String cronExpression;

    /**
     * 状态 0正常 -1停止
     */
    @Schema(description = "状态")
    private Boolean status;
}
