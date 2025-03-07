package com.hb0730.zoom.quartz;

import cn.hutool.core.convert.Convert;
import com.hb0730.zoom.base.enums.TaskCategoryEnums;
import com.hb0730.zoom.base.utils.DateUtil;
import com.hb0730.zoom.quartz.task.ZoomTaskExecutor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务导出
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
@Setter
@Slf4j
@DisallowConcurrentExecution
public class TaskExpJob extends AbstractJob {

    @Autowired
    ZoomTaskExecutor taskExecutor;

    @Override
    protected void doExecute(JobExecutionContext context) throws JobExecutionException {
        Object appName = context.getJobDetail().getJobDataMap().get("appName");
        log.info(">>>异步导出定时任务开始 ! 时间 :{}", DateUtil.now());
        taskExecutor.doTask(TaskCategoryEnums.EXPORT, Convert.toStr(appName));
        log.info("<<<异步导出定时任务结束 ! 时间 :{}", DateUtil.now());
    }
}
