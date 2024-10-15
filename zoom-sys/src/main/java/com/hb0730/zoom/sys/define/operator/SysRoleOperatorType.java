package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.operator.log.core.annotation.Module;
import com.hb0730.zoom.operator.log.core.enums.OperatorRiskLevel;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/15
 */
@Module("basic:role")
public class SysRoleOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "sys:role:create";
    public static final String EDIT = "sys:role:edit";
    public static final String DELETE = "sys:role:delete";
    public static final String GRANT = "sys:role:grant";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevel.L, ADD, "新增角色"),
                new OperatorType(OperatorRiskLevel.M, EDIT, "修改角色"),
                new OperatorType(OperatorRiskLevel.H, DELETE, "删除角色"),
                new OperatorType(OperatorRiskLevel.M, GRANT, "授权")
        };
    }
}
