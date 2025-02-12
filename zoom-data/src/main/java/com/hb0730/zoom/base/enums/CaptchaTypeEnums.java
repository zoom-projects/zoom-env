package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;
import lombok.Getter;

/**
 * 验证码类型
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/24
 */
public enum CaptchaTypeEnums implements PairEnum<String, Pair<String, String>> {

    LOGIN_SMS(MessageTypeEnums.SMS, new Pair<>("SMS_1000", "登录短信验证码")),
    LOGIN_EMAIL(MessageTypeEnums.EMAIL, new Pair<>("EMAIL_1000", "登录邮箱验证码")),

    CAPTCHA_SMS(MessageTypeEnums.SMS, new Pair<>("SMS_1001", "短信验证码")),
    CAPTCHA_EMAIL(MessageTypeEnums.EMAIL, new Pair<>("EMAIL_1001", "邮箱验证码")),
    ;
    @Getter
    private final MessageTypeEnums msgType;
    private final Pair<String, String> type;

    CaptchaTypeEnums(MessageTypeEnums msgType, Pair<String, String> type) {
        this.msgType = msgType;
        this.type = type;
    }

    @Override
    public Pair<String, String> getValue() {
        return type;
    }

    @Override
    public String getCode() {
        return this.type.getCode();
    }

    @Override
    public String getMessage() {
        return this.type.getMessage();
    }
}
