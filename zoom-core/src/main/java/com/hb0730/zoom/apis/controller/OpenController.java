package com.hb0730.zoom.apis.controller;

import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.MapUtil;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/24
 */
@Controller
@Slf4j
@Validated
public class OpenController extends RouterController {

    /**
     * 通用API入口
     *
     * @param apiName 接口名称
     * @param token   token
     * @return 结果
     */
    @RequestMapping(path = "/open/api", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PermitAll
    public ResponseEntity<Map<String, Object>> execute(@NotBlank(message = "apiName不为空") String apiName,
                                                       String token,
                                                       @RequestParam(required = false) Map<String, Object> params,
                                                       @RequestBody(required = false) Map<String, Object> body,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        Map<String, Object> allParams = MapUtil.mergeMap(params, body);
        R<?> result = null;
        if (isLimitRate(apiName, token)) {
            result = R.NG(ErrorCode.C10004.getCode(), ErrorCode.C10004.getMessage());
        } else {
            result = execute(apiName, token, allParams, request, response);
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
