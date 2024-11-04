package com.hb0730.zoom.base.sys.system.entity;

import com.hb0730.zoom.base.entity.BizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 开放接口
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysOpenApi extends BizEntity {
    /**
     * 接口标识
     */
    private String apiCode;
    /**
     * 接口名称
     */
    private String apiName;
    /**
     * 所属模块
     */
    private String module;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Boolean status = true;
}
