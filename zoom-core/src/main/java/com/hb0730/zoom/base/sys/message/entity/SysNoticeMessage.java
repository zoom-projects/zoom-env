package com.hb0730.zoom.base.sys.message.entity;

import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统通知消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysNoticeMessage extends BaseEntity {

    /**
     * 状态 0-未读 1-已读
     */
    private Integer status;
    /**
     * 分类
     */
    private String classify;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 接收人ID
     */
    private String receiverId;
    /**
     * 接收人
     */
    private String receiver;
}
