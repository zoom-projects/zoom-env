package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.sys.biz.system.convert.SysNoticeMessageConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysNoticeMessageMapper;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysNoticeMessageVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@org.springframework.stereotype.Service
@Slf4j
public class SysNoticeMessageService extends SuperServiceImpl<String, SysNoticeMessageQueryRequest,
        SysNoticeMessageVO, SysNoticeMessage, SysNoticeMessageCreateRequest, SysNoticeMessageCreateRequest,
        SysNoticeMessageMapper, SysNoticeMessageConvert> {
}
