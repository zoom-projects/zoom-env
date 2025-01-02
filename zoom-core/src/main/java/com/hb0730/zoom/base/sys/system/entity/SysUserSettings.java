package com.hb0730.zoom.base.sys.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户设置
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user_settings")
public class SysUserSettings extends BaseEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 是否开启消息通知
     */
    @Deprecated
    private Boolean messageNotification;
}
