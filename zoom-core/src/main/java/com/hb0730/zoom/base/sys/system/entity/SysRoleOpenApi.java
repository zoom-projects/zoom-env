package com.hb0730.zoom.base.sys.system.entity;

import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRoleOpenApi extends BaseEntity {
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 开放接口ID
     */
    private String openApiId;
}
