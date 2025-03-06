package com.hb0730.zoom.base.ext.services.dto;

import com.hb0730.zoom.base.enums.MessageContentTypeEnums;
import lombok.Data;

import java.io.Serializable;

/**
 * 发送消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/12
 */
@Data
public class SendMessageDTO implements Serializable {
    /**
     * 接受者
     */
    private String receiver;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    /**
     * 消息参数
     */
    private String msgParams;
    /**
     * 消息类型
     */
    private MessageContentTypeEnums contentType = MessageContentTypeEnums.TEXT;
}
