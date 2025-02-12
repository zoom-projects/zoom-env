package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 通知消息状态
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
public enum NoticeMessageStatusEnums implements PairEnum<Integer, Pair<Integer, String>> {
    UN_READ(new Pair<>(0, "未读")),
    READ(new Pair<>(1, "已读"));
    private final Pair<Integer, String> status;

    NoticeMessageStatusEnums(Pair<Integer, String> status) {
        this.status = status;
    }

    @Override
    public Pair<Integer, String> getValue() {
        return status;
    }

    @Override
    public Integer getCode() {
        return status.getCode();
    }

    @Override
    public String getMessage() {
        return status.getMessage();
    }
}
