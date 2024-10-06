package com.hb0730.zoom.base.sys.notifty.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.hb0730.zoom.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统消息模板
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysMessageTemplate extends BaseEntity {

    /**
     * 模板类型
     * <p>
     * 1-短信
     * 2-邮件
     */
    private String templateType;

    /**
     * 模板CODE
     */
    private String templateCode;

    /**
     * 模板标题
     */
    private String templateName;

    /**
     * 模板内容{0}为占位符
     */
    private String templateContent;

    /**
     * 状态，0-禁用 1-启用
     */
    private Integer status;
    /**
     * 删除标识
     */
    @TableLogic
    private Integer delFlag;


}
