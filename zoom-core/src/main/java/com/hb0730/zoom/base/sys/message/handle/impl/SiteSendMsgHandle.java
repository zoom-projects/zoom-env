package com.hb0730.zoom.base.sys.message.handle.impl;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.enums.NoticeClassifyEnums;
import com.hb0730.zoom.base.enums.NoticeMessageStatusEnums;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.base.sys.message.handle.ISendMsgHandle;
import com.hb0730.zoom.base.sys.message.service.NoticeMessageService;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.sys.system.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 站内消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/30
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SiteSendMsgHandle implements ISendMsgHandle {
    private final NoticeMessageService noticeMessageService;
    private final UserService userService;

    @Override
    public void sendMsg(String receiver, String title, String content) {
        log.info("站内消息发送开始 {} : {} -> {}", title, content, receiver);
        SysUser user = userService.findByUsername(receiver);
        if (null == user) {
            log.error("站内消息发送失败，用户不存在:{}", receiver);
            return;
        }
        SysNoticeMessage sysNoticeMessage = new SysNoticeMessage();
        sysNoticeMessage.setReceiverId(user.getId());
        sysNoticeMessage.setReceiver(user.getUsername());
        sysNoticeMessage.setClassify(NoticeClassifyEnums.NOTICE.getCode());
        sysNoticeMessage.setTitle(title);
        sysNoticeMessage.setContent(content);
        sysNoticeMessage.setStatus(NoticeMessageStatusEnums.UN_READ.getCode());
        noticeMessageService.save(sysNoticeMessage);
        log.info("站内消息发送成功 {} : {} -> {}", title, content, receiver);
    }

    @Override
    public MessageTypeEnums getType() {
        return MessageTypeEnums.SITE;
    }
}
