package com.hb0730.zoom.apis.controller;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.api.Api;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.MapUtil;
import jakarta.annotation.security.PermitAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/24
 */
@Controller
@Slf4j
public class OpenController extends RouterController {

    /**
     * 通用API入口
     * application/x-www-form-urlencoded
     *
     * @param apiName 接口名称
     * @param token   token
     * @return 结果
     */
    @RequestMapping(path = "/open/api", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PermitAll
    public ResponseEntity<Map<String, Object>> execute(String apiName, String token,
                                                       @RequestBody(required = false) Map<String, Object> body,
                                                       Map<String, Object> params) {
        Map<String, Object> allParams = MapUtil.mergeMap(body, params);
        R<?> result = null;
        if (isLimitRate(apiName, token)) {
            result = R.NG(ErrorCode.C10004.getCode(), ErrorCode.C10004.getMessage());
        } else {
            result = execute(apiName, token, allParams);
        }

        //统一响应
        Map<String, Object> res = new HashMap<>();
        res.put("success", result.isSuccess());
        res.put("code", result.isSuccess() ? ErrorCode.C10000.getCode() : result.getCode());
        res.put("desc", result.getMessage());
        res.put("data", result.getData());
        res.put("timestamp", getTimestamp(null));
        log.info("~~~ 接口响应参数 ==> {}", JsonUtil.DEFAULT.toJson(res));
        return ResponseEntity.ok(res);
    }

    @Override
    public R<?> execute(String apiName, String token, Map<String, Object> params) {
        log.info("~~~ 接口请求参数 ==> {}:{}:{}", apiName, token, JsonUtil.DEFAULT.toJson(params));
        R<?> result;
        try {
            // alibaba sentinel 限流
            //ContextUtil.enter(resourceName, apiName.replace('.', '-'));
            // 定义一个sentinel保护的资源
            //entry = SphU.entry(resourceName);
            //entry = SphU.entry(apiName);

            //确定接口
            Api api = apiAdapter.getApi(apiName);
            if (api == null) {
                result = R.NG(ErrorCode.C10005.getCode(), ErrorCode.C10005.getMessage());
                return result;
            }
            // 是否需要验证Token
            if (!api.isSkipAuth()) {
                //验证Token是否有效
                if (!checkToken(token, apiName)) {
                    result = R.NG(ErrorCode.C10003.getCode(), ErrorCode.C10003.getMessage());
                    return result;
                }
            }

            //业务处理
            return apiAdapter.execute(apiName, token, params);
        } catch (Exception e) {
            log.error("~接口请求参数 ==> {}:{}", apiName, e.getMessage(), e);
            result = R.NG(ErrorCode.C10001.getCode(), ErrorCode.C10001.getMessage());
            return result;
        }
    }

    public enum ErrorCode implements PairEnum<String, Pair<String, String>> {
        /**
         * Success
         */
        C10000(new Pair<>("10000", "Success")),
        /**
         * 系统错误
         */
        C10001(new Pair<>("10001", "系统错误")),
        /**
         * 参数错误
         */
        C10002(new Pair<>("10002", "参数错误，请参考API文档")),
        /**
         * 无效的token
         */
        C10003(new Pair<>("10003", "无效的token")),
        /**
         * 已被限流
         */
        C10004(new Pair<>("10004", "已被限流，请稍后再试")),
        /**
         * 目标不存在
         */
        C10005(new Pair<>("10005", "目标不存在")),

        ;


        /**
         * 错误代码
         */
        private final Pair<String, String> status;

        ErrorCode(Pair<String, String> status) {
            this.status = status;
        }

        @Override
        public Pair<String, String> getValue() {
            return status;
        }

        @Override
        public String getCode() {
            return status.getCode();
        }

        @Override
        public String getMessage() {
            return status.getMessage();
        }
    }

}
