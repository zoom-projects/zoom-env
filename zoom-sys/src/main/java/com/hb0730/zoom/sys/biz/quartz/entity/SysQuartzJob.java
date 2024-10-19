package com.hb0730.zoom.sys.biz.quartz.entity;

import com.hb0730.zoom.base.entity.BizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysQuartzJob extends BizEntity {
    /**
     * 所属系统代码
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
     * 描述
     */
    private String description;
    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 状态 0正常 -1停止
     */
    private Boolean status;
}
