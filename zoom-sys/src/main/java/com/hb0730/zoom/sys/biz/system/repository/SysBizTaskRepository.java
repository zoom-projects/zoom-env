package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.biz.entity.SysBizTask;
import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.sys.biz.system.convert.SysBizTaskConvert;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysBizTaskVO;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysBizTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysBizTaskRepository extends BaseRepository<String, SysBizTaskQueryRequest, SysBizTaskVO, SysBizTask,
        SysBizTaskCreateRequest, SysBizTaskCreateRequest, SysBizTaskMapper, SysBizTaskConvert> {

    /**
     * 根据任务ID更新任务状态
     *
     * @param taskState     任务状态
     * @param taskStartTime 任务开始时间
     * @param taskId        任务ID
     */
    public boolean updateTaskStateById(Integer taskState, String taskStartTime, String taskId) {
        LambdaUpdateWrapper<SysBizTask> updateWrapper = Wrappers.lambdaUpdate(SysBizTask.class)
                .set(SysBizTask::getDisState, taskState)
                .set(SysBizTask::getDisTimeBegin, taskStartTime)
                .eq(SysBizTask::getId, taskId);
        return update(updateWrapper);
    }

    /**
     * 获取任务
     *
     * @param category 分类
     * @param disState 任务状态
     * @param appName  应用名称
     * @return 任务
     */
    public SysBizTask getTask(String category, Integer disState, String appName) {
        return baseMapper.of(query -> query
                .eq(SysBizTask::getCategory, category)
                .eq(SysBizTask::getDisState, disState)
                .eq(SysBizTask::getBusinessType, appName)
        ).one();
    }
}
