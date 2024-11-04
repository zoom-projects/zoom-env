package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.operator.log.core.annotation.Module;
import com.hb0730.zoom.operator.log.core.enums.OperatorRiskLevel;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@Module("basic:open:api")
public class OpenApiOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "open:api:create";
    public static final String UPDATE = "open:api:edit";
    public static final String DELETE = "open:api:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevel.L, ADD, "新增开放接口"),
                new OperatorType(OperatorRiskLevel.M, UPDATE, "修改开放接口"),
                new OperatorType(OperatorRiskLevel.H, DELETE, "删除开放接口")
        };
    }
}
