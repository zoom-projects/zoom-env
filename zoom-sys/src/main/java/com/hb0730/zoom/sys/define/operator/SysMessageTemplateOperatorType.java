package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@OperatorModule("basic:message:template")
public class SysMessageTemplateOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "sys:message:template:add";
    public static final String EDIT = "sys:message:template:edit";
    public static final String DELETE = "sys:message:template:delete";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "添加消息模板"),
                new OperatorType(OperatorRiskLevelEnums.M, EDIT, "编辑消息模板"),
                new OperatorType(OperatorRiskLevelEnums.H, DELETE, "删除消息模板")
        };
    }
}
