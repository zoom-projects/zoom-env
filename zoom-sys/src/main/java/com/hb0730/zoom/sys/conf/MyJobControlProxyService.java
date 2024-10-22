package com.hb0730.zoom.sys.conf;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.ext.services.dto.QuartzJobDTO;
import com.hb0730.zoom.base.ext.services.remote.JobControlRpcService;
import com.hb0730.zoom.sofa.rpc.core.service.BaseRpcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Service
@Slf4j
public class MyJobControlProxyService extends BaseRpcService<JobControlRpcService> implements JobControlRpcService {


    @Override
    protected String getAppName(String which) {
        return which;
    }

    @Override
    protected String getAppName() {
        throw new NotImplementedException("QUARTZ任务控制相关RPC接口");
    }


    @Override
    public R<?> add(QuartzJobDTO params) {
        return getRpcService(params.getAppName()).add(params);
    }

    @Override
    public R<?> edit(QuartzJobDTO params) {
        return getRpcService(params.getAppName()).edit(params);
    }

    @Override
    public R<?> delete(QuartzJobDTO params) {
        return getRpcService(params.getAppName()).delete(params);
    }

    @Override
    public R<?> pauseJob(QuartzJobDTO params) {
        return getRpcService(params.getAppName()).pauseJob(params);
    }

    @Override
    public R<?> resumeJob(QuartzJobDTO params) {
        return getRpcService(params.getAppName()).resumeJob(params);
    }

    @Override
    public R<?> run(QuartzJobDTO params) {
        return getRpcService(params.getAppName()).run(params);
    }
}
