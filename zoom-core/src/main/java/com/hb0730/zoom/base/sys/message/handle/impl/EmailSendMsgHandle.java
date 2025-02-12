package com.hb0730.zoom.base.sys.message.handle.impl;

import com.hb0730.zoom.base.enums.MessageContentTypeEnums;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SendMessageDTO;
import com.hb0730.zoom.base.sys.message.handle.ISendMsgHandle;
import com.hb0730.zoom.mail.core.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 邮件发送
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/12
 */
@Component
@Slf4j
public class EmailSendMsgHandle implements ISendMsgHandle {
    @Override
    public void sendMsg(String receiver, String title, String content) {
        MailUtil.sendText(receiver, title, content);
    }

    @Override
    public void sendMsg(SendMessageDTO message) {
        MessageContentTypeEnums contentType = message.getContentType();
        switch (contentType) {
            case TEXT:
                MailUtil.sendText(message.getReceiver(), message.getTitle(), message.getContent());
                break;
            case HTML:
                MailUtil.sendHtml(message.getReceiver(), message.getTitle(), message.getContent());
                break;
            default:
                log.error("不支持的类型,{}", contentType);
        }
    }

    @Override
    public MessageTypeEnums getType() {
        return MessageTypeEnums.EMAIL;
    }
}
