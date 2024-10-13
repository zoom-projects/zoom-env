package com.hb0730.zoom.sys.define.cache;

import com.hb0730.zoom.cache.redis.config.CustomCacheConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存配置
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/12
 */
@Component
public class CacheConfig implements CustomCacheConfig {
    @Override
    public long getDefaultExpiration() {
        return 8 * 60 * 60; //8小时
    }

    @Override
    public Map<String, Long> getCustomCacheConfigs() {
        Map<String, Long> customCacheConfigs = new HashMap<>();
        customCacheConfigs.put(SysDictCacheKeyDefine.DICT_ITEMS_KEY, 8 * 60 * 60L);
        customCacheConfigs.put(SysDictCacheKeyDefine.DICT_ITEMS_KEY + ":dictValueType", 8 * 60 * 60L);
        return customCacheConfigs;
    }
}
