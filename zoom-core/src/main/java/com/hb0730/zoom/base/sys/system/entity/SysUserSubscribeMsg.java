package com.hb0730.zoom.base.sys.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户订阅消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserSubscribeMsg extends BaseEntity {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 订阅ID
     *
     * @see com.hb0730.zoom.base.sys.message.entity.SysMessageSubscribe
     */
    private String subscribeId;

    /**
     * 是否站内消息
     */
    private Boolean isSite;

    /**
     * 是否邮件
     */
    private Boolean isEmail;

    /**
     * 是否短信
     */
    private Boolean isSms;
    /**
     * 是否微信
     */
    @TableField(exist = false)
    private Boolean isWechat;
}
