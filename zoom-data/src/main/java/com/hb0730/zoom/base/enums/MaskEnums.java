package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

/**
 * 脱敏枚举
 * <ul>
 *  <li>1-中文</li>
 *  <li>2-身份证号</li>
 *  <li>3-手机号</li>
 *  <li>4-邮箱</li>
 *  <li>5-银行卡号</li>
 *  <li>6-地址</li>
 *  <li>7-api密钥</li>
 * </ul>
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
public enum MaskEnums implements PairEnum<String, Pair<String, String>> {
    CHINESE(new Pair<>("1", "中文")),
    /**
     * 身份证号
     */
    ID_CARD(new Pair<>("2", "身份证号")),
    /**
     * 手机号
     */
    Mobile(new Pair<>("3", "手机号")),
    /**
     * 固定电话
     */
    FIX_PHONE(new Pair<>("4", "固定电话")),
    /**
     * 邮箱
     */
    EMAIL(new Pair<>("5", "邮箱")),
    /**
     * 银行卡号
     */
    BANK_CARD(new Pair<>("6", "银行卡号")),
    /**
     * 地址
     */
    ADDRESS(new Pair<>("7", "地址")),
    /**
     * api密钥
     */
    API_KEY(new Pair<>("8", "api密钥")),
    ;

    private final Pair<String, String> status;

    MaskEnums(Pair<String, String> status) {
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
