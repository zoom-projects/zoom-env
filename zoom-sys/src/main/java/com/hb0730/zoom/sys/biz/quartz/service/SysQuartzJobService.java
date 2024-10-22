package com.hb0730.zoom.sys.biz.quartz.service;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.ext.services.dto.QuartzJobDTO;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.sys.biz.quartz.convert.SysQuartzJobConvert;
import com.hb0730.zoom.sys.biz.quartz.entity.SysQuartzJob;
import com.hb0730.zoom.sys.biz.quartz.mapper.SysQuartzJobMapper;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzCreateRequest;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzJobQueryRequest;
import com.hb0730.zoom.sys.biz.quartz.model.vo.SysQuartzJobVO;
import com.hb0730.zoom.sys.conf.MyJobControlProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Service
@Slf4j
public class SysQuartzJobService extends SuperServiceImpl<String, SysQuartzJobQueryRequest, SysQuartzJobVO,
        SysQuartzJob, SysQuartzCreateRequest, SysQuartzCreateRequest, SysQuartzJobMapper, SysQuartzJobConvert> {
    @Autowired
    private MyJobControlProxyService jobControlProxyService;

    /**
     * 通过任务类名查找
     *
     * @param jobClassName 任务类名
     * @return 是否存在
     */
    public boolean findByJobClassName(String jobClassName) {
        return baseMapper.of(query -> query.eq(SysQuartzJob::getJobClassName, jobClassName))
                .present();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(SysQuartzCreateRequest request) {
        SysQuartzJob entity = getMapstruct().createReqToEntity(request);
        save(entity);
        QuartzJobDTO dto = getMapstruct().toDto(request);
        dto.setId(entity.getId());
        R<?> res = jobControlProxyService.add(dto);
        if (!res.isSuccess()) {
            //物理删除
            baseMapper.deleteById(entity.getId());
            throw new ZoomException("创建任务失败 " + res.getMessage());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(String id, SysQuartzCreateRequest request) {
        super.updateById(id, request);
        //更新任务
        QuartzJobDTO dto = getMapstruct().toDto(request);
        dto.setId(id);
        R<?> res = jobControlProxyService.edit(dto);
        if (!res.isSuccess()) {
            throw new ZoomException("更新任务失败 " + res.getMessage());
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
        super.removeById(id);
        //删除任务
        QuartzJobDTO dto = new QuartzJobDTO();
        dto.setId((String) id);
        R<?> res = jobControlProxyService.delete(dto);
        if (!res.isSuccess()) {
            throw new ZoomException("删除任务失败 " + res.getMessage());
        }
        return true;
    }

    /**
     * 暂停任务
     *
     * @param id 任务id
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public R<String> pauseJob(String id) {
        SysQuartzJob entity = getById(id);
        if (entity == null) {
            return R.NG("任务不存在");
        }
        entity.setStatus(false);
        updateById(entity);
        QuartzJobDTO dto = mapstruct.toDto(entity);
        R<?> res = jobControlProxyService.pauseJob(dto);
        if (!res.isSuccess()) {
            throw new ZoomException("暂停任务失败 " + res.getMessage());
        }
        return R.OK();
    }

    /**
     * 恢复任务
     *
     * @param id 任务id
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public R<String> resumeJob(String id) {
        SysQuartzJob entity = getById(id);
        if (entity == null) {
            return R.NG("任务不存在");
        }
        entity.setStatus(true);
        updateById(entity);
        QuartzJobDTO dto = mapstruct.toDto(entity);
        R<?> res = jobControlProxyService.resumeJob(dto);
        if (!res.isSuccess()) {
            throw new ZoomException("恢复任务失败 " + res.getMessage());
        }
        return R.OK();
    }

    /**
     * 执行任务
     *
     * @param id 任务id
     * @return 结果
     */
    public R<String> executeJob(String id) {
        SysQuartzJob entity = getById(id);
        if (entity == null) {
            return R.NG("任务不存在");
        }
        QuartzJobDTO dto = mapstruct.toDto(entity);
        R<?> res = jobControlProxyService.run(dto);
        if (!res.isSuccess()) {
            throw new ZoomException("执行任务失败 " + res.getMessage());
        }
        return R.OK();
    }
}
