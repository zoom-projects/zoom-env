package com.hb0730.zoom.base.sys.message.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.base.sys.message.mapper.NoticeMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Service
@Slf4j
public class NoticeMessageService extends ServiceImpl<NoticeMessageMapper, SysNoticeMessage> {
}
