package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.message.entity.SysMessageSubscribe;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeGroupVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeVO;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysMessageSubscribeConvert extends BizMapstruct<SysMessageSubscribeVO, SysMessageSubscribe,
        SysMessageSubscribeCreateRequest, SysMessageSubscribeUpdateRequest> {


    /**
     * 转换成模块
     *
     * @param list 列表
     * @return 模块
     */
    List<SysMessageSubscribeGroupVO.SysMessageSubscribeChildVO> toGroup(List<SysMessageSubscribeVO> list);
}
