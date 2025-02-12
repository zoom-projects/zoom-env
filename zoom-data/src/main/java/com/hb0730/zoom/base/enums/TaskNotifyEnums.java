package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 任务通知
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/29
 */
public enum TaskNotifyEnums implements PairEnum<String, Pair<String, String>> {
    COMPLETED(new Pair<>("TASK_001", "任务完成")),
    ;
    private final Pair<String, String> type;

    TaskNotifyEnums(Pair<String, String> type) {
        this.type = type;
    }

    @Override
    public Pair<String, String> getValue() {
        return type;
    }

    @Override
    public String getCode() {
        return type.getCode();
    }

    @Override
    public String getMessage() {
        return type.getMessage();
    }
}
