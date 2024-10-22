package com.hb0730.zoom.sys.biz.quartz.convert;

import com.hb0730.zoom.base.ext.services.dto.QuartzJobDTO;
import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.sys.biz.quartz.entity.SysQuartzJob;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzCreateRequest;
import com.hb0730.zoom.sys.biz.quartz.model.vo.SysQuartzJobVO;
import org.mapstruct.Mapping;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysQuartzJobConvert extends BizMapstruct<SysQuartzJobVO, SysQuartzJob,
        SysQuartzCreateRequest, SysQuartzCreateRequest> {

    /**
     * 转换
     *
     * @param request 请求
     * @return dto
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "enabled", source = "status")
    QuartzJobDTO toDto(SysQuartzCreateRequest request);

    /**
     * 转换
     *
     * @param entity 实体
     * @return dto
     */
    @Mapping(target = "enabled", source = "status")
    QuartzJobDTO toDto(SysQuartzJob entity);


}
