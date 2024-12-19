package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@OperatorModule("basic:open:api")
public class OpenApiOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "open:api:create";
    public static final String UPDATE = "open:api:edit";
    public static final String DELETE = "open:api:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "新增开放接口"),
                new OperatorType(OperatorRiskLevelEnums.M, UPDATE, "修改开放接口"),
                new OperatorType(OperatorRiskLevelEnums.H, DELETE, "删除开放接口")
        };
    }
}
