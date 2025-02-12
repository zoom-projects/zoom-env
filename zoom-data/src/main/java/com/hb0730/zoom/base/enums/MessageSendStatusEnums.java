package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 发送消息状态
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/12
 */
public enum MessageSendStatusEnums implements PairEnum<Integer, Pair<Integer, String>> {
    WAITING(new Pair<>(0, "等待发送")),
    SUCCESS(new Pair<>(1, "发送成功")),
    FAILURE(new Pair<>(2, "发送失败")),
    ;

    private final Pair<Integer, String> status;

    MessageSendStatusEnums(Pair<Integer, String> status) {
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
