package com.hb0730.zoom.base.sys.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hb0730.zoom.base.entity.BizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 消息订阅管理
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMessageSubscribe extends BizEntity {
    /**
     * 归属模块
     */
    private String module;
    /**
     * 标识
     */
    @TableField("`code`")
    private String code;
    /**
     * 名称
     */
    @TableField("`name`")
    private String name;
    /**
     * 描述
     */
    @TableField("`desc`")
    private String desc;
}
