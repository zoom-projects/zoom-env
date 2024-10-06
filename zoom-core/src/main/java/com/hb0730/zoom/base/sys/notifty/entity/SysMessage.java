package com.hb0730.zoom.base.sys.notifty.entity;

import com.hb0730.zoom.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMessage extends BaseEntity {
    /**
     * 消息类型
     * <p>
     * 1-短信
     * 2-邮件
     */
    private String msType;
    /**
     * 消息标题
     */
    private String msTitle;
    /**
     * 消息接收者
     */
    private String msReceiver;
    /**
     * 消息参数
     */
    private String msParam;
    /**
     * 消息内容
     */
    private String msContent;

    /**
     * 推送时间
     */
    private Date msSendTime;
    /**
     * 推送状态
     * -1-不再推送
     * 0-未推送
     * 1-推送成功
     * 2-推送失败
     */
    private Integer msSendStatus;
    /**
     * 推送次数
     */
    private Integer msSendNum;
    /**
     * 推送失败原因
     */
    private String msResult;
    /**
     * 备注
     */
    private String remark;

}
