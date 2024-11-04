package com.hb0730.zoom.apis.manager;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.api.ApiManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/24
 */
@Component
@RequiredArgsConstructor
public class ApiAdapter extends DefaultApiManager {
    /**
     * 对外接口管理器
     */
    @Autowired
    ApiManager apiManager;

    /**
     * 执行
     *
     * @param apiName api名称
     * @param token   token
     * @param params  参数
     * @return 结果
     */
    public R<?> execute(String apiName, String token, Map<String, Object> params) {
        if (null != apiManager) {
            return apiManager.getApi(apiName).execute(token, params);
        }
        return R.NG("没有默认接口管理器！");
    }

    /**
     * 验证token是否有效
     *
     * @param token   token
     * @param apiName 接口名称
     * @return 结果
     */
    @Override
    public boolean checkToken(String token, String apiName) {
        return apiManager.checkToken(token, apiName);
    }
}
