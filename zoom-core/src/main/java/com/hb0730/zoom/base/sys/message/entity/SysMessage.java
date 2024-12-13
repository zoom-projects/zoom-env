package com.hb0730.zoom.base.sys.message.entity;

import com.hb0730.zoom.base.entity.BizEntity;
import com.hb0730.zoom.mybatis.core.annotation.FieldEncrypt;
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
public class SysMessage extends BizEntity {
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
    @FieldEncrypt(decrypt = false)
    private String msgReceiver;
    /**
     * 消息参数
     */
    @FieldEncrypt(decrypt = false)
    private String msgParam;
    /**
     * 消息内容
     */
    @FieldEncrypt(decrypt = false)
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
