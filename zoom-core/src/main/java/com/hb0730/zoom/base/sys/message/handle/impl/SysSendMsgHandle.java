package com.hb0730.zoom.base.sys.message.handle.impl;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.sys.message.handle.ISendMsgHandle;
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
public class SysSendMsgHandle implements ISendMsgHandle {
    @Override
    public void sendMsg(String receiver, String title, String content) {

    }

    @Override
    public MessageTypeEnums getType() {
        return MessageTypeEnums.SITE;
    }
}
