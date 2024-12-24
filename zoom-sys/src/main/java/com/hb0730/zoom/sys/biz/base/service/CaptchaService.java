package com.hb0730.zoom.sys.biz.base.service;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.CaptchaTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.sys.message.service.MessageService;
import com.hb0730.zoom.base.utils.Md5Util;
import com.hb0730.zoom.base.utils.RandomUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import com.hb0730.zoom.cache.core.define.CacheKeyDefine;
import com.hb0730.zoom.sys.define.cache.LoginCaptchaCacheKeyDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

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
        // 加密
        CacheKeyDefine cacheKeyDefine = LoginCaptchaCacheKeyDefine.LOGIN_CAPTCHA;
        String _key = Md5Util.md5Hex(key);
        Optional<String> cacheString = cache.getString(cacheKeyDefine.format(_key));
        if (cacheString.isPresent() && cacheString.get().equals(code)) {
            return R.OK();
        }
        if (cacheString.isEmpty()) {
            return R.NG("验证码已过期,请重新获取");
        }
        return R.NG("验证码错误");
    }

    /**
     * 发送验证码
     *
     * @param type    类型
     * @param account 账号
     * @return 验证码
     */
    public R<String> sendCode(CaptchaTypeEnums type, String account) {
        String code = RandomUtil.randomNumbers(6);
        setCache(account, code);
        sendNotify(type, account, code);
        return R.OK();
    }


    /**
     * 发送通知
     *
     * @param type    类型
     * @param account 账号
     * @param code    验证码
     */
    private void sendNotify(CaptchaTypeEnums type, String account, String code) {
        SaveMessageDTO dto = new SaveMessageDTO();
        dto.setReceiver(account);
        dto.setTemplateCode(type.getCode());
        dto.setMsgType(type.getMsgType());
        Map<String, String> extra = Map.of("code", code);
        dto.setExtra(extra);
        messageService.saveMessage(dto);
    }

    private void setCache(String key, String code) {
        // 加密
        CacheKeyDefine cacheKeyDefine = LoginCaptchaCacheKeyDefine.LOGIN_CAPTCHA;
        String _key = Md5Util.md5Hex(key);
        cache.setString(
                cacheKeyDefine.format(_key),
                code,
                cacheKeyDefine.getTimeout(),
                cacheKeyDefine.getUnit());
    }
}
