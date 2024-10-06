package com.hb0730.zoom.base.sys.system.entity;

import com.hb0730.zoom.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 系统通告
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysNotice extends BaseEntity {
    /**
     * 标题
     */
    private String titile;
    /**
     * 摘要
     */
    private String msgAbstract;
    /**
     * 内容
     */
    private String msgContent;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 发布人
     */
    private String sender;
    /**
     * 优先级（L低，M中，H高）
     */
    private String priority;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    private String msgCategory;
    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    private String msgType;
    /**
     * 指定接收用户IDs
     */
    private String userIds;

    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    private String sendStatus;
    /**
     * 发布时间
     */
    private Date sendTime;
    /**
     * 撤销时间
     */
    private Date cancelTime;
}
