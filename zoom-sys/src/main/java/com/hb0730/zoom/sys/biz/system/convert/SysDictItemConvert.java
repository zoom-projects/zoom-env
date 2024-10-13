package com.hb0730.zoom.sys.biz.system.convert;

import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.sys.system.entity.SysDictItem;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysDictItemVO;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/12
 */
@org.mapstruct.Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface SysDictItemConvert extends BizMapstruct<SysDictItemVO, SysDictItem, SysDictItemCreateRequest,
        SysDictItemCreateRequest> {
}
