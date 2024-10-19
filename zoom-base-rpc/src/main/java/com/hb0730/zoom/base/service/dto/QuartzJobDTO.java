package com.hb0730.zoom.base.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Data
@EqualsAndHashCode
public class QuartzJobDTO implements Serializable {
    private String id;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 任务类名
     */
    private String jobClassName;
    /**
     * 参数
     */
    private String parameter;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 状态
     */
    private Boolean enabled;

    /**
     * 描述
     */
    private String description;
}
