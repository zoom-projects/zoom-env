package com.hb0730.zoom.quartz.service;

import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.ext.services.dto.QuartzJobDTO;
import com.hb0730.zoom.base.utils.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JobManager {
    private static final String JOB_NAME = "TASK_";
    private final Scheduler scheduler;
    private final BeanFactory beanFactory;

    /**
     * 添加定时任务
     *
     * @param quartzJob 定时任务
     */
    public void jobAdd(QuartzJobDTO quartzJob) {
        try {
            // 构建job信息
            String className = quartzJob.getJobClassName();
            if (StrUtil.isBlank(className)) {
                throw new RuntimeException("定时任务类名不能为空");
            }

            Class<? extends Job> targetClazz = getClass(className);
            if (targetClazz == null) {
                throw new RuntimeException("定时任务类未找到");
            }
            // 启动调度器
            scheduler.start();

            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob()
                    .ofType(targetClazz)
                    .withIdentity(JOB_NAME + quartzJob.getId())
                    .usingJobData("parameter", quartzJob.getParameter())
                    .usingJobData("appName", quartzJob.getAppName())
                    .withDescription(quartzJob.getDescription())
                    .build();


            // 表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                    .cronSchedule(quartzJob.getCronExpression());

            //通过触发器名和cron 表达式创建 Trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(JOB_NAME + quartzJob.getId())
                    .withSchedule(cronScheduleBuilder)
                    .withDescription(quartzJob.getDescription())
                    .build();
            //执行定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("定时任务添加失败", e);
            throw new ZoomException("定时任务添加失败", e);
        }
    }


    /**
     * 一次性执行定时任务
     *
     * @param quartzJob 定时任务
     */
    public void jobRunOnce(QuartzJobDTO quartzJob) {
        try {
            // 可能是一次性任务，所以不需要启动调度器
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + quartzJob.getId());
            // 通过触发器名和组名获取Trigger
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 是否存在
            boolean exists = trigger != null;
            if (trigger == null) {
                jobAdd(quartzJob);
            }
            // 重新执行
            scheduler.triggerJob(JobKey.jobKey(JOB_NAME + quartzJob.getId()));
            if (!exists) {
                jobDelete(quartzJob.getId());
            }
        } catch (Exception e) {
            log.error("定时任务执行失败", e);
            throw new ZoomException("定时任务执行失败", e);
        }
    }

    /**
     * 暂停定时任务
     *
     * @param id .
     */
    public void jobPause(String id) {
        JobKey jobKey = JobKey.jobKey(JOB_NAME + id);
        try {
            scheduler.pauseJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务暂停失败", e);
            throw new ZoomException("定时任务暂停失败", e);
        }
    }

    /**
     * 恢复定时任务
     *
     * @param job .
     */
    public void jobResume(QuartzJobDTO job) {
        JobKey jobKey = JobKey.jobKey(JOB_NAME + job.getId());
        try {
            // 任务是否存在，存在则恢复，不存在则添加
            if (!scheduler.checkExists(jobKey)) {
                jobAdd(job);
            }
            scheduler.resumeJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务恢复失败", e);
            throw new ZoomException("定时任务恢复失败", e);
        }
    }

    /**
     * 删除定时任务
     *
     * @param id .
     */
    public void jobDelete(String id) {
        JobKey jobKey = JobKey.jobKey(JOB_NAME + id);
        try {
            // 停止触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(JOB_NAME + id));
            // 移除触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(JOB_NAME + id));
            // 删除任务
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            log.error("定时任务删除失败", e);
            throw new ZoomException("定时任务删除失败", e);
        }
    }

    /**
     * 实例化指定任务实现类
     *
     * @param jobClassName 任务实现类全名/bean名称
     * @return 任务实现类实例
     * @throws Exception .
     */
    @SuppressWarnings("unchecked")
    private Class<? extends Job> getClass(String jobClassName) throws Exception {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(jobClassName);
        } catch (Exception e) {
            clazz = beanFactory.getType(jobClassName);
        }
        return (Class<? extends Job>) clazz;
    }
}
