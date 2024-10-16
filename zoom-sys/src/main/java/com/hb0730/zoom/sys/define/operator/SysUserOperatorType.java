package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.operator.log.core.annotation.Module;
import com.hb0730.zoom.operator.log.core.enums.OperatorRiskLevel;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/14
 */
@Module("basic:user")
public class SysUserOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "sys:user:create";
    public static final String EDIT = "sys:user:edit";
    public static final String DELETE = "sys:user:delete";
    public static final String RESET_PASSWORD = "sys:user:reset:password";
    public static final String GRANT_ROLE = "sys:user:grant:role";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevel.L, ADD, "新增用户"),
                new OperatorType(OperatorRiskLevel.M, EDIT, "修改用户"),
                new OperatorType(OperatorRiskLevel.H, DELETE, "删除用户"),
                new OperatorType(OperatorRiskLevel.M, RESET_PASSWORD, "重置密码"),
                new OperatorType(OperatorRiskLevel.M, GRANT_ROLE, "授权角色"),
        };
    }
}
