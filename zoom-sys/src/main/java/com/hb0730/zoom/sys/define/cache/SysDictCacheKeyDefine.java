package com.hb0730.zoom.sys.define.cache;

import com.hb0730.zoom.cache.core.define.CacheKeyBuilder;
import com.hb0730.zoom.cache.core.define.CacheKeyDefine;
import com.hb0730.zoom.cache.core.define.struct.RedisCacheStruct;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 数据字典 缓存key 定义
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/12
 */
public interface SysDictCacheKeyDefine {
    CacheKeyDefine DICT_ITEMS = CacheKeyBuilder.create()
            .type(List.class)
            .key("dict:items:{}")
            .desc("数据字典项 {dictCode}")
            .struct(RedisCacheStruct.STRING)
            .timeout(8, TimeUnit.HOURS)
            .build();
}
