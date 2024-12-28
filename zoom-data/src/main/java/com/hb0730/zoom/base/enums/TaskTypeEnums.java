package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 任务类型
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
public enum TaskTypeEnums implements PairEnum<String, Pair<String, String>> {
    /**
     * 00:啥也不做
     */
    T00(new Pair<String, String>("00", "啥也不做")),

    /**
     * 01:导出
     */
    E01(new Pair<String, String>("E01", "语录导出")),


    /**
     * 01:导入
     */
    I01(new Pair<String, String>("I01", "语录导入")),
    ;

    private final Pair<String, String> pair;

    TaskTypeEnums(Pair<String, String> pair) {
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
