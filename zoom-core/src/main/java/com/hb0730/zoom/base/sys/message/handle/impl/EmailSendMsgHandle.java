package com.hb0730.zoom.base.sys.message.handle.impl;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.sys.message.handle.ISendMsgHandle;
import com.hb0730.zoom.mail.core.MailUtil;

/**
 * 邮件发送
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/12
 */
public class EmailSendMsgHandle implements ISendMsgHandle {
    @Override
    public void sendMsg(String receiver, String title, String content) {
        MailUtil.sendText(receiver, title, content);
    }

    @Override
    public MessageTypeEnums getType() {
        return MessageTypeEnums.EMAIL;
    }
}
