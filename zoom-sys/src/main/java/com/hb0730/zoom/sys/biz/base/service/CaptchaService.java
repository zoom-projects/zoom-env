package com.hb0730.zoom.sys.biz.base.service;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.LoginCaptchaMsgTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.sys.notifty.service.MessageService;
import com.hb0730.zoom.base.utils.Md5Util;
import com.hb0730.zoom.base.utils.RandomUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.cache.core.define.CacheKeyDefine;
import com.hb0730.zoom.sys.define.cache.LoginCaptchaCacheKeyDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 验证码服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Service
public class CaptchaService {
    @Autowired
    private MessageService messageService;
    @Autowired
    private CacheUtil cache;

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
     * 发送验证码
     *
     * @param type    类型
     * @param account 账号
     * @return 验证码
     */
    public R<String> sendCode(String type, String account) {
        String code = RandomUtil.randomNumbers(6);
        setCache(account, code);
        sendNotify(type, account, code);
        return R.OK(code);
    }


    /**
     * 发送通知
     *
     * @param type    类型
     * @param account 账号
     * @param code    验证码
     */
    private void sendNotify(String type, String account, String code) {
        SaveMessageDTO dto = new SaveMessageDTO();
        dto.setReceiver(account);
        dto.setTemplateCode(LoginCaptchaMsgTypeEnums.of(type).getCode());
        dto.setMsgType(LoginCaptchaMsgTypeEnums.of(type).getMsgType().getCode());
        Map<String, String> extra = Map.of("code", code);
        dto.setExtra(extra);
        messageService.saveMessage(dto);
    }

    private void setCache(String key, String code) {
        // 加密
        CacheKeyDefine cacheKeyDefine = LoginCaptchaCacheKeyDefine.LOGIN_CAPTCHA;
        String _key = Md5Util.md5Hex(key);
        cache.setJson(
                cacheKeyDefine.format(_key),
                code,
                cacheKeyDefine.getTimeout(),
                cacheKeyDefine.getUnit());
    }
}
