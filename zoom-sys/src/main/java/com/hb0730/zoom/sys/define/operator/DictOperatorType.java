package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.Module;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * 字典操作类型
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
@Module("basic:dict")
public class DictOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "dict:create";
    public static final String UPDATE = "dict:edit";
    public static final String DELETE = "dict:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "新增字典"),
                new OperatorType(OperatorRiskLevelEnums.M, UPDATE, "修改字典"),
                new OperatorType(OperatorRiskLevelEnums.H, DELETE, "删除字典")
        };
    }
}
