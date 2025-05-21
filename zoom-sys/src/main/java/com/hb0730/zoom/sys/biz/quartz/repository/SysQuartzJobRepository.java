package com.hb0730.zoom.sys.biz.quartz.repository;

import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.sys.biz.quartz.convert.SysQuartzJobConvert;
import com.hb0730.zoom.sys.biz.quartz.entity.SysQuartzJob;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzCreateRequest;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzJobQueryRequest;
import com.hb0730.zoom.sys.biz.quartz.model.vo.SysQuartzJobVO;
import com.hb0730.zoom.sys.biz.quartz.repository.mapper.SysQuartzJobMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysQuartzJobRepository extends BaseRepository<String, SysQuartzJobQueryRequest, SysQuartzJobVO,
        SysQuartzJob, SysQuartzCreateRequest, SysQuartzCreateRequest, SysQuartzJobMapper, SysQuartzJobConvert> {


    /**
     * 判断任务名称是否存在
     *
     * @param jobName 任务名称
     * @return true 存在 false 不存在
     */
    public boolean JobClassNameExists(String jobName) {
        return lambdaQuery()
                .eq(SysQuartzJob::getJobClassName, jobName)
                .exists();
    }
}
