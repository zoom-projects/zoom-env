package com.hb0730.zoom.data.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 业务实体
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BizEntity extends BaseEntity {
    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status = 1;
    /**
     * 删除标识
     */
    @TableLogic
    private Integer delFlag = 0;
}
