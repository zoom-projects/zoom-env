package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
@OperatorModule("basic:dict-item")
public class DictItemOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "dict-item:create";
    public static final String UPDATE = "dict-item:edit";
    public static final String DELETE = "dict-item:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "新增字典项"),
                new OperatorType(OperatorRiskLevelEnums.M, UPDATE, "修改字典项"),
                new OperatorType(OperatorRiskLevelEnums.H, DELETE, "删除字典项")
        };
    }
}
