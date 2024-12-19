package com.hb0730.zoom.sys.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@OperatorModule("basic:quartz")
public class SysQuartzJobOperatorType extends InitializingOperatorTypes {
    public static final String ADD = "sys:quartz:add";
    public static final String EDIT = "sys:quartz:edit";
    public static final String DELETE = "sys:quartz:delete";
    public static final String OPERATE = "sys:quartz:operator";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, ADD, "添加"),
                new OperatorType(OperatorRiskLevelEnums.M, EDIT, "编辑"),
                new OperatorType(OperatorRiskLevelEnums.H, DELETE, "删除"),
                new OperatorType(OperatorRiskLevelEnums.M, OPERATE, "操作任务(暂停/启动/运行)"),
        };
    }
}
