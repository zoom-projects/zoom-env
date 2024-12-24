package com.hb0730.zoom.apis.controller;

import com.hb0730.zoom.apis.manager.ApiAdapter;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.api.Api;
import com.hb0730.zoom.base.api.ApiDescription;
import com.hb0730.zoom.base.ext.services.dto.SaveOperatorLogDTO;
import com.hb0730.zoom.base.ext.services.dto.UserDTO;
import com.hb0730.zoom.base.ext.services.proxy.SysProxyService;
import com.hb0730.zoom.base.meta.TraceHolder;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.ServletUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/24
 */
@Slf4j
public class RouterController {
    @Autowired
    protected ApiAdapter apiAdapter;
    @Autowired
    private SysProxyService proxyService;

    /**
     * 接口处理
     *
     * @param apiName  接口名称
     * @param token    token
     * @param params   参数
     * @param response
     * @return 结果
     */
    public R<?> execute(String apiName, String token, Map<String, Object> params,
                        HttpServletRequest request, HttpServletResponse response) {
        log.info("~~~ 接口请求参数 ==> {}:{}:{}", apiName, token, JsonUtil.DEFAULT.toJson(params));
        R<?> result = null;
        try {
            // alibaba sentinel 限流
            //ContextUtil.enter(resourceName, apiName.replace('.', '-'));
            // 定义一个sentinel保护的资源
            //entry = SphU.entry(resourceName);
            //entry = SphU.entry(apiName);

            //确定接口
            Api api = apiAdapter.getApi(apiName);
            if (api == null) {
                result = R.NG(OpenController.ErrorCode.C10005.getCode(), OpenController.ErrorCode.C10005.getMessage());
                return result;
            }
            // 是否需要验证Token
            if (!api.isSkipAuth()) {
                if (StrUtil.isBlank(token)) {
                    return R.NG("token不能为空");
                }
                //验证Token是否有效
                if (!checkToken(token, apiName)) {
                    result = R.NG(OpenController.ErrorCode.C10003.getCode(), OpenController.ErrorCode.C10003.getMessage());
                    return result;
                }
            }

            //业务处理
            result = apiAdapter.execute(apiName, token, params);
            return result;
        } catch (Exception e) {
            log.error("~~~ 接口异常 ==> {}:{}", apiName, e.getMessage(), e);
            result = R.NG(OpenController.ErrorCode.C10001.getCode(), OpenController.ErrorCode.C10001.getMessage());
            return result;
        } finally {
            // 记录日志
            doSomethingOnFinally(token, apiName, params, result, request);
        }
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
    protected long getTimestamp(@Nullable String timestamp) {
        boolean isUnixTimestamp = StrUtil.isNotBlank(timestamp) && timestamp.length() == 10;
        if (isUnixTimestamp) {
            return LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        } else {
            return System.currentTimeMillis();
        }
    }

    /**
     * 日志访问记录
     *
     * @param token     访问token
     * @param apiName   接口名称
     * @param allParams 请求参数
     * @param result    响应结果
     */
    public void doSomethingOnFinally(String token,
                                     String apiName,
                                     Map<String, Object> allParams,
                                     R<?> result,
                                     HttpServletRequest request) {
        try {
            Api api = apiAdapter.getApi(apiName);
            if (api == null) {
                return;
            }
            // api描述
            ApiDescription description = api.description();

            Optional<UserDTO> userOptional = Optional.of(proxyService.findUserByToken(token));
            SaveOperatorLogDTO dto = new SaveOperatorLogDTO();
            // 操作人ID
            dto.setUserId(userOptional.map(UserDTO::getUserId).orElse("summery"));
            // 操作人
            dto.setUsername(userOptional.map(UserDTO::getUsername).orElse("summery"));
            // traceId
            // opentelemetry traceId
            String traceId = TraceHolder.getOtelTraceId();
            dto.setTraceId(traceId);
            // 请求ip address
            dto.setAddress(ServletUtil.getClientIP(request));
            // 地址 location
            // userAgent
            dto.setUserAgent(ServletUtil.getUserAgent(request));
            // 操作模块 module
            dto.setModule(description.getModule());
            // 操作类型 type
            dto.setType(apiName);
            // 风险等级 riskLevel
            dto.setRiskLevel(description.getRiskLevel());
            // 操作内容 logInfo
            dto.setLogInfo("summary");
            // 请求参数 extra
            dto.setExtra("");
            // result 操作结果，0-成功，1-失败
            dto.setResult(result.isSuccess() ? 0 : 1);
            // 错误信息 errorMessage
            dto.setErrorMessage(result.getMessage());
            // 返回值 returnValue
            dto.setReturnValue(JsonUtil.DEFAULT.toJson(result));
            // 操作时间 duration
            // 开始时间 startTime
            // 结束时间 endTime
        } catch (Exception e) {
            log.error("~~~接口访问日志记录异常~~~", e);
        }
    }
}
