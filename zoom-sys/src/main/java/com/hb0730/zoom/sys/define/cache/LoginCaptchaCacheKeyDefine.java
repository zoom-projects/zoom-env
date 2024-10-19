package com.hb0730.zoom.sys.define.cache;

import com.hb0730.zoom.cache.core.define.CacheKeyBuilder;
import com.hb0730.zoom.cache.core.define.CacheKeyDefine;
import com.hb0730.zoom.cache.core.define.struct.RedisCacheStruct;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
public interface LoginCaptchaCacheKeyDefine {

    CacheKeyDefine LOGIN_CAPTCHA = CacheKeyBuilder.create()
            .type(String.class)
            .key("sys:login:captcha:{}")
            .type(String.class)
            .desc("登录验证码 {key}")
            .struct(RedisCacheStruct.STRING)
            //10分钟
            .timeout(60 * 10)
            .build();
}
