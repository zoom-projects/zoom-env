package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 通知分类
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
public enum NoticeClassifyEnums implements PairEnum<String, Pair<String, String>> {
    NOTICE(new Pair<>("NOTICE", "通知")),
    //公告
    ANNOUNCEMENT(new Pair<>("ANNOUNCEMENT", "公告")),
    ;
    private final Pair<String, String> scene;

    NoticeClassifyEnums(Pair<String, String> scene) {
        this.scene = scene;
    }

    @Override
    public Pair<String, String> getValue() {
        return scene;
    }

    @Override
    public String getCode() {
        return scene.getCode();
    }

    @Override
    public String getMessage() {
        return scene.getMessage();
    }
}
