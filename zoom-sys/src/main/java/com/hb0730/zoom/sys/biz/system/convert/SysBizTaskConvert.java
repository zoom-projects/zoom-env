package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.biz.entity.SysBizTask;
import com.hb0730.zoom.base.ext.services.dto.task.BizTaskCreateDTO;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysBizTaskVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysBizTaskConvert extends BizMapstruct<SysBizTaskVO, SysBizTask, SysBizTaskCreateRequest, SysBizTaskCreateRequest> {

    /**
     * toCreateRequest
     *
     * @param dto dto
     * @return SysBizTaskCreateRequest
     */
    SysBizTaskCreateRequest toCreateRequest(BizTaskCreateDTO dto);


    /**
     * toTaskInfo
     *
     * @param bizTask bizTask
     * @return TaskInfo
     */
    TaskInfo toTaskInfo(SysBizTask bizTask);
}
