package com.hb0730.zoom.quartz.remote;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.service.dto.QuartzJobDTO;
import com.hb0730.zoom.base.service.remote.JobControlRpcService;
import com.hb0730.zoom.quartz.service.JobManager;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@RemoteService
@Slf4j
public class JobControlRemoteService implements JobControlRpcService {
    @Autowired
    private JobManager jobManager;

    @Override
    public R<?> add(QuartzJobDTO params) {
        jobManager.jobAdd(params);
        if (Boolean.FALSE.equals(params.getEnabled())) {
            pauseJob(params);
        }
        return R.OK();
    }

    @Override
    public R<?> edit(QuartzJobDTO params) {
        if (Boolean.TRUE.equals(params.getEnabled())) {
            jobManager.jobDelete(params.getId());
            jobManager.jobAdd(params);
        } else {
            jobManager.jobPause(params.getId());
        }
        return R.OK();
    }

    @Override
    public R<?> delete(QuartzJobDTO params) {
        jobManager.jobDelete(params.getId());
        return R.OK();
    }

    @Override
    public R<?> pauseJob(QuartzJobDTO params) {
        jobManager.jobPause(params.getId());
        return R.OK();
    }

    @Override
    public R<?> resumeJob(QuartzJobDTO params) {
        jobManager.jobResume(params);
        return R.OK();
    }

    @Override
    public R<?> run(QuartzJobDTO params) {
        jobManager.jobRunOnce(params);
        return R.OK();
    }
}
