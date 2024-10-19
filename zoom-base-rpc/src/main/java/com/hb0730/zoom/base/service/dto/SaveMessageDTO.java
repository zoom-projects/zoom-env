package com.hb0730.zoom.base.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * 保存消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Data
@EqualsAndHashCode

public class SaveMessageDTO implements Serializable {
    /**
     * 消息内容
     */
    private String msgType;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 消息参数
     */
    private String msgParams;
    /**
     * 额外参数
     */
    private Map<String, String> extra;
}
