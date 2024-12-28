package com.hb0730.zoom.base.ext.services.remote;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.TaskCategoryEnums;
import com.hb0730.zoom.base.ext.services.dto.task.BizTaskCreateDTO;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.sofa.rpc.core.annotation.RpcAppName;

/**
 * 业务任务RPC服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@RpcAppName("zoom-app")
public interface SysBizTaskRpcService {


    /**
     * 保存任务
     *
     * @return 任务ID
     */
    R<String> saveTask(BizTaskCreateDTO dto);

    /**
     * 获取工作中的任务
     *
     * @param taskCategory 任务类型
     * @param appName      应用名称
     * @return 任务
     */
    TaskInfo getWorkingTask(TaskCategoryEnums taskCategory, String appName);

    /**
     * 获取待处理任务
     *
     * @param taskCategory 任务类型
     * @return 任务
     */
    TaskInfo getPengdingTask(TaskCategoryEnums taskCategory, String appName);

    /**
     * 设置任务开始执行标志
     *
     * @param taskId        任务ID
     * @param taskState     任务状态
     * @param taskStartTime 任务开始时间
     */
    Boolean setTaskStartState(String taskId, Integer taskState, String taskStartTime);


    /**
     * 立即更新任务
     *
     * @param taskInfo 任务信息
     */
    void updateTastImmediately(TaskInfo taskInfo);
}
