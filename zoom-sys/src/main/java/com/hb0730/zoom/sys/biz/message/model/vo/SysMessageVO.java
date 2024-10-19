package com.hb0730.zoom.sys.biz.message.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
@Data
@Schema(description = "消息")
public class SysMessageVO implements java.io.Serializable {
    private String id;
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
     * 推送时间
     */
    private Date msgSendTime;
    /**
     * 推送状态
     * -1-不再推送
     * 0-未推送
     * 1-推送成功
     * 2-推送失败
     */
    private Integer msgSendStatus;
    /**
     * 推送次数
     */
    private Integer msgSendNum;
    /**
     * 推送失败原因
     */
    private String msgResult;
    /**
     * 备注
     */
    private String remark;
}
