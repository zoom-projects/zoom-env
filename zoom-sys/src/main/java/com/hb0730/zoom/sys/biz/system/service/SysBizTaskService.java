package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.biz.entity.SysBizTask;
import com.hb0730.zoom.base.enums.TaskStateEnums;
import com.hb0730.zoom.base.enums.TaskTypeEnums;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.ext.security.SecurityUtils;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.utils.IdUtil;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysBizTaskConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysBizTaskMapper;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysBizTaskVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@Service
@Slf4j
public class SysBizTaskService extends SuperServiceImpl<String, SysBizTaskQueryRequest, SysBizTaskVO, SysBizTask,
        SysBizTaskCreateRequest, SysBizTaskCreateRequest, SysBizTaskMapper, SysBizTaskConvert> {
    @Autowired
    private SysSerialNumberService serialNumberService;

    /**
     * 保存任务
     *
     * @param request 任务信息
     * @return 任务号
     */
    public R<String> saveTask(SysBizTaskCreateRequest request) {
        String type = request.getType();
        Optional<TaskTypeEnums> typeEnums = PairEnum.of(TaskTypeEnums.class, type);
        if (typeEnums.isEmpty()) {
            throw new ZoomException("任务类型不存在");
        }
        if (TaskTypeEnums.T00.equals(typeEnums.get())) {
            throw new ZoomException("任务类型不允许创建");
        }
        TaskTypeEnums taskType = typeEnums.get();
        SysBizTask sysBizTask = new SysBizTask();
        // 任务类型 E,I
        sysBizTask.setCategory(taskType.getCode().substring(0, 1));
        //任务类型
        sysBizTask.setType(taskType.getCode());
        // 业务应用
        sysBizTask.setBusinessType(request.getAppName());
        // 任务单号
        sysBizTask.setTaskNum(getTaskNum());
        //任务描述
        sysBizTask.setMessage(taskType.getMessage());
        // 任务名称
        sysBizTask.setTaskName(taskType.getMessage());
        //批次号
        sysBizTask.setLotNo(request.getLotNo() == null ? IdUtil.getSnowflakeNextIdStr() : request.getLotNo());
        //FIXME 需要执行任务参数：查询条件
        Map<String, String> p = request.getParam();
        sysBizTask.setDisContent(JsonUtil.DEFAULT.toJson(p));
        //文件名称（保存到指定位置的上传文件） filename filePath
        String filename = request.getFileName();
        String filePath = request.getFilePath();
        String fileUrl = request.getFileUrl();
        if (StringUtils.isBlank(filename)) {
            filename = String.format("%s-%s.xlsx", taskType.getCode(), sysBizTask.getLotNo());
            filePath = SecurityUtils.getLoginUsername().orElse("summary") + "/exp";
        }
        // 文件名称
        sysBizTask.setFileName(filename);
        // 文件path
        sysBizTask.setFilePath(filePath);
        //文件地址
        sysBizTask.setFileUrl(fileUrl);

        //任务状态
        sysBizTask.setDisState(TaskStateEnums.T0.getCode());
        // 避免空指针
        if (StringUtils.isBlank(sysBizTask.getDisContent())) {
            sysBizTask.setDisContent("{}");
        }
        save(sysBizTask);
        return R.OK(sysBizTask.getTaskNum());
    }

    /**
     * 更新任务状态
     *
     * @param taskId        任务ID
     * @param taskState     任务状态
     * @param taskStartTime 任务开始时间
     * @return 是否成功
     */
    public Boolean updateTaskState(String taskId, Integer taskState, String taskStartTime) {
        LambdaUpdateWrapper<SysBizTask> updateWrapper = Wrappers.lambdaUpdate(SysBizTask.class)
                .set(SysBizTask::getDisState, taskState)
                .set(SysBizTask::getDisTimeBegin, taskStartTime)
                .eq(SysBizTask::getId, taskId);
        return update(updateWrapper);
    }

    /**
     * 获取任务号
     *
     * @return 任务号
     */
    private String getTaskNum() {
        return serialNumberService.getSerialNumber("SYS_TASK", "");
    }
}
