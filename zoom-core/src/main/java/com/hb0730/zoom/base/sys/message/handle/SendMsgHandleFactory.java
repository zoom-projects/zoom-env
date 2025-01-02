package com.hb0730.zoom.base.sys.message.handle;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 发送信息工厂
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/13
 */
@Component
public class SendMsgHandleFactory {
    public Map<MessageTypeEnums, ISendMsgHandle> CACHE = new java.util.HashMap<>();

    public SendMsgHandleFactory(ApplicationContext applicationContext) {
        applicationContext.getBeansOfType(ISendMsgHandle.class).forEach((k, v) -> CACHE.put(v.getType(), v));
    }

    /**
     * 获取发送信息
     *
     * @param type {@link MessageTypeEnums}
     * @return {@link ISendMsgHandle}
     */
    public ISendMsgHandle get(MessageTypeEnums type) {
        return CACHE.get(type);
    }
}
