package com.hb0730.zoom.quartz;

import cn.hutool.core.convert.Convert;
import com.hb0730.zoom.base.enums.TaskCategoryEnums;
import com.hb0730.zoom.base.utils.DateUtil;
import com.hb0730.zoom.quartz.task.ZoomTaskExecutor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 任务导入
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
@Slf4j
@DisallowConcurrentExecution // 不允许并发执行
@Setter
public class TaskImpJob implements Job {
    @Autowired
    ZoomTaskExecutor taskExecutor;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Object appName = context.getJobDetail().getJobDataMap().get("appName");
        log.info("异步导入定时任务开始 ! 时间 :{}", DateUtil.now());
        taskExecutor.doTask(TaskCategoryEnums.IMPORT, Convert.toStr(appName));
        log.info("异步导入定时任务结束 ! 时间 :{}", DateUtil.now());
    }
}