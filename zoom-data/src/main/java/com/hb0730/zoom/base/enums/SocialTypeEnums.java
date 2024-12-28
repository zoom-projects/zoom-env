package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 社交类型
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/14
 */
public enum SocialTypeEnums implements PairEnum<String, Pair<String, String>> {
    GITHUB(new Pair<>("github", "github登陆")),
    GITEE(new Pair<>("gitee", "gitee登陆")),
    ;
    private final Pair<String, String> status;

    SocialTypeEnums(Pair<String, String> status) {
        this.status = status;
    }

    @Override
    public Pair<String, String> getValue() {
        return status;
    }

    @Override
    public String getCode() {
        return status.getCode();
    }

    @Override
    public String getMessage() {
        return status.getMessage();
    }
}
