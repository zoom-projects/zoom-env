package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.operator.log.core.annotation.Module;
import com.hb0730.zoom.operator.log.core.enums.OperatorRiskLevel;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * 认证操作类型
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/10
 */
@Module("basic:authentication")
public class AuthenticationOperatorType extends InitializingOperatorTypes {
    public static final String LOGIN = "authentication:login";

    public static final String LOGOUT = "authentication:logout";

    public static final String UPDATE_PASSWORD = "authentication:update-password";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevel.L, LOGIN, "用户登录"),
                new OperatorType(OperatorRiskLevel.L, LOGOUT, "用户登出"),
                new OperatorType(OperatorRiskLevel.L, UPDATE_PASSWORD, "修改密码")
        };
    }
}
