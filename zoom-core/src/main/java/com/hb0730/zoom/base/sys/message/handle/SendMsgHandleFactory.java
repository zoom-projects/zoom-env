package com.hb0730.zoom.base.sys.message.handle;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.sys.message.handle.impl.EmailSendMsgHandle;

import java.util.Map;

/**
 * 发送信息工厂
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/13
 */
public class SendMsgHandleFactory {
    public static Map<MessageTypeEnums, ISendMsgHandle> CACHE = new java.util.HashMap<>();

    static {
        CACHE.put(MessageTypeEnums.EMAIL, new EmailSendMsgHandle());
    }

    /**
     * 获取发送信息
     *
     * @param type {@link MessageTypeEnums}
     * @return {@link ISendMsgHandle}
     */
    public static ISendMsgHandle get(MessageTypeEnums type) {
        return CACHE.get(type);
    }
}
