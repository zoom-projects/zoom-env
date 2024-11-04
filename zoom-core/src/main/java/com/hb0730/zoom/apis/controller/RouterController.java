package com.hb0730.zoom.apis.controller;

import com.hb0730.zoom.apis.manager.ApiAdapter;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/24
 */

public class RouterController {
    @Autowired
    protected ApiAdapter apiAdapter;

    /**
     * 接口处理
     *
     * @param apiName 接口名称
     * @param token   token
     * @param params  参数
     * @return 结果
     */
    public R<?> execute(String apiName, String token, Map<String, Object> params) {
        return apiAdapter.execute(apiName, token, params);
    }

    /**
     * 验证token是否有效
     *
     * @param token   token
     * @param apiName 接口名称
     * @return 结果
     */
    protected boolean checkToken(String token, String apiName) {
        return apiAdapter.checkToken(token, apiName);
    }

    /**
     * 是否限制速率
     *
     * @param apiName 接口名称
     * @param token   token
     * @return 结果
     */
    protected boolean isLimitRate(String apiName, String token) {
        return false;
    }

    /**
     * 取得当前时间戳
     *
     * @param timestamp 参照用时间戳
     * @return 当前时间戳
     */
    protected long getTimestamp(String timestamp) {
        boolean isUnixTimestamp = StrUtil.isNotBlank(timestamp) && timestamp.length() == 10;
        if (isUnixTimestamp) {
            return LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        } else {
            return System.currentTimeMillis();
        }
    }
}
