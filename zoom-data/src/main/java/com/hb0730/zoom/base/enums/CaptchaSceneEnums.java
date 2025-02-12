package com.hb0730.zoom.base.enums;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;

import java.util.Arrays;
import java.util.List;

/**
 * 验证码场景
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/24
 */
public enum CaptchaSceneEnums implements PairEnum<String, Pair<String, String>> {
    LOGIN(new Pair<>("login", "登录"), Arrays.asList(CaptchaTypeEnums.LOGIN_SMS, CaptchaTypeEnums.LOGIN_EMAIL)),
    REGISTER(new Pair<>("register", "注册"), null),
    GET(new Pair<>("get", "获取"), Arrays.asList(CaptchaTypeEnums.CAPTCHA_SMS, CaptchaTypeEnums.CAPTCHA_EMAIL)),
    ;
    private final Pair<String, String> scene;
    private final List<CaptchaTypeEnums> types;

    CaptchaSceneEnums(Pair<String, String> scene, List<CaptchaTypeEnums> types) {
        this.scene = scene;
        this.types = types;
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

    /**
     * 获取类型
     *
     * @param msgType 消息类型
     * @return 类型
     */
    public CaptchaTypeEnums getType(MessageTypeEnums msgType) {
        if (types == null) {
            return null;
        }
        for (CaptchaTypeEnums type : types) {
            if (type.getMsgType().equals(msgType)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 获取场景
     *
     * @param scene 场景
     * @return 场景
     */
    public static CaptchaSceneEnums get(String scene) {
        for (CaptchaSceneEnums value : CaptchaSceneEnums.values()) {
            if (value.getCode().equals(scene)) {
                return value;
            }
        }
        return null;
    }
}
