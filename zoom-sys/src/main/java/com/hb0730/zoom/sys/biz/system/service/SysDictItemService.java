package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysDictItem;
import com.hb0730.zoom.sys.biz.system.convert.SysDictItemConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysDictItemMapper;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysDictItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/12
 */
@Service
@Slf4j
public class SysDictItemService extends SuperServiceImpl<String, SysDictItemQueryRequest, SysDictItemVO, SysDictItem,
        SysDictItemCreateRequest, SysDictItemCreateRequest,
        SysDictItemMapper, SysDictItemConvert> {


}
