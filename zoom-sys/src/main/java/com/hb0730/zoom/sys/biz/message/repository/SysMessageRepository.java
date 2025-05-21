package com.hb0730.zoom.sys.biz.message.repository;

import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.message.entity.SysMessage;
import com.hb0730.zoom.sys.biz.message.convert.SysMessageConvert;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageQueryRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageVO;
import com.hb0730.zoom.sys.biz.message.repository.mapper.SysMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysMessageRepository extends BaseRepository<String, SysMessageQueryRequest, SysMessageVO, SysMessage,
        SysMessageCreateRequest, SysMessageUpdateRequest, SysMessageMapper, SysMessageConvert> {
}
