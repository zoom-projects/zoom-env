package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 启用状态
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
public enum EnableStatusEnum implements PairEnum<Integer, Pair<Integer, String>> {
    /**
     * 启用
     */
    ENABLE(new Pair<>(1, "启用")),
    /**
     * 禁用
     */
    DISABLE(new Pair<>(0, "禁用"));

    private final Pair<Integer, String> status;

    EnableStatusEnum(Pair<Integer, String> status) {
        this.status = status;
    }

    @Override
    public Pair<Integer, String> getValue() {
        return status;
    }

    @Override
    public Integer getCode() {
        return this.status.getCode();
    }

    @Override
    public String getMessage() {
        return this.status.getMessage();
    }

    public static EnableStatusEnum getEnum(Integer code) {
        for (EnableStatusEnum value : EnableStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static String getMessage(Integer code) {
        EnableStatusEnum value = getEnum(code);
        return value == null ? null : value.getMessage();
    }

    public static Integer getCode(String message) {
        for (EnableStatusEnum value : EnableStatusEnum.values()) {
            if (value.getMessage().equals(message)) {
                return value.getCode();
            }
        }
        return null;
    }

    
}
