package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysOpenApi;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiGroupVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiVO;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysOpenApiConvert extends BizMapstruct<SysOpenApiVO, SysOpenApi, SysOpenApiCreateRequest,
        SysOpenApiCreateRequest> {


    /**
     * toGroup
     *
     * @param list list
     * @return SysOpenApiGroupVO.SysOpenApiChildVO
     */
    List<SysOpenApiGroupVO.SysOpenApiChildVO> toGroup(List<SysOpenApiVO> list);

}
