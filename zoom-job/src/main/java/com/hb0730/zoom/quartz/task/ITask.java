package com.hb0730.zoom.quartz.task;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;

/**
 * 任务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
public interface ITask {
    /**
     * 获取任务类型
     *
     * @return 任务类型
     */
    String getTaskType();

    /**
     * 任务处理
     *
     * @param task 任务信息
     * @return 处理结果
     * @throws Exception 异常
     */
    R<?> doTask(TaskInfo task) throws Exception;
}
