package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 任务状态
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
public enum TaskStateEnums implements PairEnum<Integer, Pair<Integer, String>> {
    T0(new Pair<>(0, "未开始")),
    T1(new Pair<>(1, "进行中")),
    T2(new Pair<>(2, "已结束"));
    private final Pair<Integer, String> pair;

    TaskStateEnums(Pair<Integer, String> pair) {
        this.pair = pair;
    }

    @Override
    public Pair<Integer, String> getValue() {
        return pair;
    }

    @Override
    public Integer getCode() {
        return pair.getCode();
    }

    @Override
    public String getMessage() {
        return pair.getMessage();
    }
}
