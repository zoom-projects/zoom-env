package com.hb0730.zoom.base.ext.services.remote;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.ext.services.dto.QuartzJobDTO;

/**
 * 定时任务控制器RPC接口
 * =================================================================================
 * 实现
 * zoom-job#JobControlRemoteService
 * 代理
 * zoom-sys#MyJobControlProxyService
 * =================================================================================
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
public interface JobControlRpcService {
    /**
     * 新增定时任务
     *
     * @param params 任务参数
     * @return 处理结果
     */
    R<?> add(QuartzJobDTO params);

    /**
     * 更新定时任务
     *
     * @param params 任务参数
     * @return 处理结果
     */
    R<?> edit(QuartzJobDTO params);

    /**
     * 删除定时任务
     *
     * @param params 任务参数
     * @return 处理结果
     */
    R<?> delete(QuartzJobDTO params);

    /**
     * 暂停定时任务
     *
     * @param params 任务参数
     * @return 处理结果
     */
    R<?> pauseJob(QuartzJobDTO params);

    /**
     * 启动定时任务
     *
     * @param params 任务参数
     * @return 处理结果
     */
    R<?> resumeJob(QuartzJobDTO params);

    /**
     * 运行定时任务
     *
     * @param params 任务参数
     * @return 处理结果
     */
    R<?> run(QuartzJobDTO params);
}
