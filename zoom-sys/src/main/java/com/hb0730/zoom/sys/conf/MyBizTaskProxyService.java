package com.hb0730.zoom.sys.conf;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.biz.entity.SysBizTask;
import com.hb0730.zoom.base.enums.TaskCategoryEnums;
import com.hb0730.zoom.base.enums.TaskStateEnums;
import com.hb0730.zoom.base.ext.services.dto.task.BizTaskCreateDTO;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.ext.services.remote.SysBizTaskRpcService;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import com.hb0730.zoom.sofa.rpc.core.service.BaseRpcService;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskCreateRequest;
import com.hb0730.zoom.sys.biz.system.service.SysBizTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@RemoteService
@Slf4j
public class MyBizTaskProxyService extends BaseRpcService<SysBizTaskRpcService> implements SysBizTaskRpcService {
    @Autowired
    private SysBizTaskService sysBizTaskService;
    ;

    @Override
    protected String getAppName() {
        throw new UnsupportedOperationException("业务任务相关RPC接口");
    }

    @Override
    public R<String> saveTask(BizTaskCreateDTO dto) {
        SysBizTaskCreateRequest createRequest = sysBizTaskService.getRepository().getMapstruct().toCreateRequest(dto);
        sysBizTaskService.create(createRequest);
        return R.OK();
    }

    @Override
    public TaskInfo getWorkingTask(TaskCategoryEnums taskCategory, String appName) {
//        LambdaQueryWrapper<SysBizTask> queryWrapper = Wrappers.lambdaQuery(SysBizTask.class)
//                .eq(SysBizTask::getCategory, taskCategory.getCode())
//                .eq(SysBizTask::getDisState, TaskStateEnums.T1.getCode())
//                .eq(SysBizTask::getBusinessType, appName);
//        SysBizTask bizTask = sysBizTaskService.getOne(queryWrapper, false);
        SysBizTask bizTask = sysBizTaskService.getTask(
                taskCategory.getCode(),
                TaskStateEnums.T1.getCode(),
                appName
        );
        if (bizTask != null) {
            return sysBizTaskService.getRepository().getMapstruct().toTaskInfo(bizTask);
        }
        return null;
    }

    @Override
    public TaskInfo getPengdingTask(TaskCategoryEnums taskCategory, String appName) {
//        LambdaQueryWrapper<SysBizTask> queryWrapper = Wrappers.lambdaQuery(SysBizTask.class)
//                .eq(SysBizTask::getCategory, taskCategory.getCode())
//                .eq(SysBizTask::getDisState, TaskStateEnums.T0.getCode())
//                .eq(SysBizTask::getBusinessType, appName);
//        SysBizTask bizTask = sysBizTaskService.getOne(queryWrapper, false);
        SysBizTask bizTask = sysBizTaskService.getTask(
                taskCategory.getCode(),
                TaskStateEnums.T0.getCode(),
                appName
        );
        if (bizTask != null) {
            return sysBizTaskService.getRepository().getMapstruct().toTaskInfo(bizTask);
        }
        return null;
    }

    @Override
    public Boolean setTaskStartState(String taskId, Integer taskState, String taskStartTime) {
        return sysBizTaskService.updateTaskState(taskId, taskState, taskStartTime);
    }

    @Override
    public void updateTastImmediately(TaskInfo taskInfo) {
        SysBizTask dbTask = sysBizTaskService.getById(taskInfo.getId());
        dbTask.setDisState(taskInfo.getDisState());
        dbTask.setDisCount(taskInfo.getDisCount());
        dbTask.setFailCount(taskInfo.getFailCount());
        dbTask.setDisTimeEnd(taskInfo.getDisTimeEnd());
        dbTask.setMessage(taskInfo.getMessage());
        dbTask.setFileName(taskInfo.getFileName());
        dbTask.setFileUrl(taskInfo.getFileUrl());
        sysBizTaskService.updateById(dbTask);
    }
}
