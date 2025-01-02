package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@OperatorModule("basic:message:subscribe")
public class SysMessageSubscribeOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "sys:message:subscribe:create";
    public static final String UPDATE = "sys:message:subscribe:edit";
    public static final String DELETE = "sys:message:subscribe:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "新增消息订阅"),
                new OperatorType(OperatorRiskLevelEnums.M, UPDATE, "修改消息订阅"),
                new OperatorType(OperatorRiskLevelEnums.H, DELETE, "删除消息订阅")
        };
    }
}
