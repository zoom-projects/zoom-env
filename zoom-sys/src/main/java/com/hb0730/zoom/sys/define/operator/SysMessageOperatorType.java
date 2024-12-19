package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
@OperatorModule("basic:message")
public class SysMessageOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "message:create";
    public static final String UPDATE = "message:edit";
    public static final String DELETE = "message:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "新增消息"),
                new OperatorType(OperatorRiskLevelEnums.M, UPDATE, "修改消息"),
                new OperatorType(OperatorRiskLevelEnums.M, DELETE, "删除消息")
        };
    }
}
