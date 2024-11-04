package com.hb0730.zoom.apis.manager;

import com.hb0730.zoom.base.AppUtil;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.api.Api;
import com.hb0730.zoom.base.api.ApiManager;
import com.hb0730.zoom.base.api.ApiService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/24
 */
public abstract class DefaultApiManager implements ApiManager {
    /**
     * 缓存
     */
    private final Map<String, Api> CACHE = new ConcurrentHashMap<>();


    @Override
    public Api getApi(String apiName) {
        if (CACHE.isEmpty()) {
            synchronized (CACHE) {
                if (CACHE.isEmpty()) {
                    init();
                }
            }
        }
        Api api = CACHE.get(apiName);
        if (api == null) {
            api = new Api() {

                @Override
                public R<?> execute(String token, Map<String, Object> params) {
                    return R.NG(String.format("未找到对应的api:%s", apiName));
                }

                @Override
                public boolean isSkipAuth() {
                    return false;
                }
            };
        }
        return api;
    }

    /**
     * 初始化
     */
    protected void init() {
        CACHE.putAll(AppUtil.getBeansWithAnnotation(ApiService.class, Api.class));
    }
}
