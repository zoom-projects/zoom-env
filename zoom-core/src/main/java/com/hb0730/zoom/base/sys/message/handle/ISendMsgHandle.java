package com.hb0730.zoom.base.sys.message.handle;

import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SendMessageDTO;

/**
 * 发送信息接口
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/12
 */
public interface ISendMsgHandle {

    /**
     * 发送信息
     *
     * @param receiver 接受人
     * @param title    标题
     * @param content  内容
     */
    void sendMsg(String receiver, String title, String content);

    /**
     * 发送信息
     *
     * @param message 消息
     */
    default void sendMsg(SendMessageDTO message) {
        return;
    }

    /**
     * 获取类型
     *
     * @return {@link MessageTypeEnums}
     */
    MessageTypeEnums getType();
}
