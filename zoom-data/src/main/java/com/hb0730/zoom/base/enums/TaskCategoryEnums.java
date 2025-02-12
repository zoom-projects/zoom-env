package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
public enum TaskCategoryEnums implements PairEnum<String, Pair<String, String>> {
    /**
     * 导出
     */
    EXPORT(new Pair<>("E", "普通导出")),
    /**
     * 导入
     */
    IMPORT(new Pair<>("I", "普通导入")),
    ;

    private final Pair<String, String> pair;

    TaskCategoryEnums(Pair<String, String> pair) {
        this.pair = pair;
    }

    @Override
    public Pair<String, String> getValue() {
        return pair;
    }

    @Override
    public String getCode() {
        return pair.getCode();
    }

    @Override
    public String getMessage() {
        return pair.getMessage();
    }
}
