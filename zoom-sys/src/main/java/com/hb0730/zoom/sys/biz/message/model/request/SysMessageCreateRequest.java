package com.hb0730.zoom.sys.biz.message.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
@Schema(description = "创建消息")
@Data
public class SysMessageCreateRequest implements Serializable {
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息标题
     */
    private String msgTitle;
    /**
     * 消息接收者
     */
    private String msgReceiver;
    /**
     * 消息参数
     */
    private String msgParam;
    /**
     * 消息内容
     */
    private String msgContent;
    /**
     * 备注
     */
    private String remark;
}
