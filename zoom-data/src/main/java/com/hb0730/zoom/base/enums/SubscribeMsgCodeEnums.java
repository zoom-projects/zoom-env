package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
public enum SubscribeMsgCodeEnums implements PairEnum<String, Pair<String, String>> {
    GOROKU_AUDIT_MSG(new Pair<>("gorokuAuditMsg", "goroku审核消息")),
    BASIC_TASK_MSG(new Pair<>("basicTaskMsg", "任务上传/下载等相关消息")),
    ;
    private final Pair<String, String> pair;

    SubscribeMsgCodeEnums(Pair<String, String> pair) {
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
