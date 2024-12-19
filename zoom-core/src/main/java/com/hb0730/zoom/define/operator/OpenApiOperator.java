package com.hb0730.zoom.define.operator;

import com.hb0730.zoom.base.enums.OperatorRiskLevelEnums;
import com.hb0730.zoom.operator.log.core.annotation.OperatorModule;
import com.hb0730.zoom.operator.log.core.factory.InitializingOperatorTypes;
import com.hb0730.zoom.operator.log.core.model.OperatorType;

/**
 * 开放API操作
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/10
 */
@OperatorModule("open-api")
public class OpenApiOperator extends InitializingOperatorTypes {
    /**
     * 模块
     */
    public static final String MODULE = "open-api";
    /**
     * 获取goroku信息
     */
    public static final String GOROKU_GET = "open-api:goroku.get";

    @Override
    public OperatorType[] types() {
        return new OperatorType[]{
                new OperatorType(OperatorRiskLevelEnums.L, GOROKU_GET, "获取goroku信息"),
        };
    }
}
