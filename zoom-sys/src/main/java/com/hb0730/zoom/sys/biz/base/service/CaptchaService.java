package com.hb0730.zoom.sys.biz.base.service;

import com.hb0730.zoom.base.R;
import org.springframework.stereotype.Service;

/**
 * 验证码服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Service
public class CaptchaService {

    /**
     * 校验验证码
     *
     * @param key  验证码key
     * @param code 验证码
     * @return 是否成功
     */
    public R<String> checkCaptcha(String key, String code) {
        return R.
                NG("验证码错误,暂时不支持验证码校验");
    }

    /**
     * 发送短信验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public R<String> sendSmsCode(String phone) {
        return R.NG("验证码发送失败,暂时不支持短信验证码");
    }

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱
     * @return 验证码
     */
    public R<String> sendEmailCode(String email) {
        return R.NG("验证码发送失败,暂时不支持邮箱验证码");
    }
}
