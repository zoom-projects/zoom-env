package com.hb0730.zoom.quartz;

import cn.hutool.core.convert.Convert;
import com.hb0730.zoom.base.AppUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/3/5
 */
@Slf4j
public abstract class AbstractJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (isSkip(context)) {
            log.info("~~~定时任务跳过本次执行~~~");
            return;
        }
        doExecute(context);
    }

    protected boolean isSkip(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String jobSkipFlag = Convert.toStr(jobDataMap.get("JOB_SKIP_FLAG"), "");
        if ("1".equals(jobSkipFlag)) {
            return true;
        }

        try {

            CacheUtil cacheUtil = AppUtil.getBean(CacheUtil.class);
            return Optional.of("1").equals(cacheUtil.getString("SYS_JOB_SKIP_FLAG"));
        } catch (Exception ignored) {

        }
        return false;
    }

    protected abstract void doExecute(JobExecutionContext context) throws JobExecutionException;
}
