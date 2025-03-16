package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.CaptchaSceneEnums;
import com.hb0730.zoom.base.enums.CaptchaTypeEnums;
import com.hb0730.zoom.base.enums.LoginGrantEnums;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.ext.security.SecurityUtils;
import com.hb0730.zoom.base.meta.UserInfo;
import com.hb0730.zoom.base.pool.RegexPool;
import com.hb0730.zoom.base.pool.StrPool;
import com.hb0730.zoom.base.utils.AesCryptoUtil;
import com.hb0730.zoom.base.utils.Base64Util;
import com.hb0730.zoom.base.utils.HexUtil;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.RegexUtil;
import com.hb0730.zoom.base.utils.SecureUtil;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.social.core.SocialAuthRequestFactory;
import com.hb0730.zoom.sys.biz.base.granter.TokenGranterBuilder;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.base.model.request.CodeLoginRequest;
import com.hb0730.zoom.sys.biz.base.model.request.EmailLoginRequest;
import com.hb0730.zoom.sys.biz.base.model.request.PhoneLoginRequest;
import com.hb0730.zoom.sys.biz.base.model.request.SocialCallbackRequest;
import com.hb0730.zoom.sys.biz.base.model.request.SocialLoginRequest;
import com.hb0730.zoom.sys.biz.base.model.request.UsernameLoginRequest;
import com.hb0730.zoom.sys.biz.base.service.AuthUserService;
import com.hb0730.zoom.sys.biz.base.service.CaptchaService;
import com.hb0730.zoom.sys.define.operator.AuthenticationOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 用户认证
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证服务")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final TokenGranterBuilder tokenGranterBuilder;
    @Autowired
    private CaptchaService captchaService;
    private final SocialAuthRequestFactory socialAuthRequestFactory;
    private final AuthUserService authUserService;

    /**
     * 用户登录
     */
    @PermitAll
    @PostMapping("/login/{type}")
    @Operation(summary = "用户登录,根据不同类型登录，password:用户名密码登录，mobile:手机号登录,social:社交登录,email:邮箱登录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "登录成功,返回token", responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "登录失败", responseCode = "400")
    })
    @OperatorLog(value = AuthenticationOperatorType.LOGIN)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "登录请求",
            required = true,
            content = {
                    @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json;charset=UTF-8",
                            schemaProperties = {
                                    @io.swagger.v3.oas.annotations.media.SchemaProperty(
                                            name = "password",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = UsernameLoginRequest.class
                                            )
                                    ),
                                    @io.swagger.v3.oas.annotations.media.SchemaProperty(
                                            name = "mobile",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    implementation = PhoneLoginRequest.class
                                            )
                                    )
                            },
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "password",
                                            description = "用户名密码登录",
                                            value = "{\"username\":\"admin\",\"password\":\"123456\",\"captchaKey\":\"123456\",\"timestamp\":\"123456\"}"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "code",
                                            description = "验证码登陆",
                                            value = "{\"username\":\"123456\",\"captchaKey\":\"123456\"," +
                                                    "\"timestamp\":\"123456\"}"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "social",
                                            description = "社交登录",
                                            value = "{\"socialSource\":\"github\",\"socialCode\":\"123456\"," +
                                                    "\"socialState\":\"123456\"}"
                                    )
                            }
                    )
            }
    )
    public R<String> login(@PathVariable String type,
                           @RequestBody Map<String, String> body) {
        Optional<LoginGrantEnums> grantEnums = PairEnum.of(LoginGrantEnums.class, type);
        if (grantEnums.isEmpty()) {
            return R.NG("暂不支持该登录方式");
        }
        return switch (grantEnums.get()) {
            case PASSWORD -> {
                UsernameLoginRequest dto = UsernameLoginRequest.of(body);
                yield loginByUsername(dto);
            }
            // 废弃 使用code登录
            case MOBILE -> {
                PhoneLoginRequest dto = PhoneLoginRequest.of(body);
                yield loginByMobile(dto);
            }
            // 废弃 使用code登录
            case EMAIL -> {
                EmailLoginRequest loginRequest = EmailLoginRequest.of(body);
                yield loginByEmail(loginRequest);
            }
            case CODE -> {
                CodeLoginRequest loginRequest = CodeLoginRequest.of(body);
                yield loginByCode(loginRequest);
            }
            case SOCIAL -> {
                SocialLoginRequest loginRequest = SocialLoginRequest.of(body);
                yield loginBySocial(loginRequest);
            }
            default -> R.NG("暂不支持该登录方式");
        };
    }

    /**
     * 获取验证码
     *
     * @param scene   类型 login,register,captcha
     * @param msgType 消息类型 email, sms
     * @param body    参数
     * @return 验证码
     */
    @PostMapping("/captcha/{scene}/{msgType}")
    @Operation(summary = "获取验证码")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "获取验证码", responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "获取验证码失败", responseCode = "400")
    })
    @Parameters({
            @Parameter(name = "scene", description = "场景", required = true),
            @Parameter(name = "msgType", description = "消息类型", required = true)
    })
    @PermitAll
    public R<?> getCaptchaCode(@PathVariable String scene,
                               @PathVariable String msgType, @RequestBody Map<String, String> body) {
        CaptchaSceneEnums sceneEnums = CaptchaSceneEnums.get(scene);
        if (sceneEnums == null) {
            return R.NG("暂不支持该类型方式获取验证码");
        }
        MessageTypeEnums _msgType = MessageTypeEnums.of(msgType);
        if (_msgType == null) {
            return R.NG("暂不支持该类型方式获取验证码");
        }
        CaptchaTypeEnums captchaType = sceneEnums.getType(_msgType);
        // 解密
        String iv = body.get("captchaKey");
        String timestamp = body.get("timestamp");
        String key = SecureUtil.sha256(iv + timestamp);
        byte[] _key = HexUtil.decodeHex(key);
        byte[] _iv = iv.getBytes();
        String account = AesCryptoUtil.decrypt(body.get("value"), AesCryptoUtil.mode, _key, _iv);
        // 获取验证码
        return captchaService.sendCode(captchaType, account);
    }


    /**
     * 社交登录
     *
     * @param source 类型
     * @return 结果
     */
    @GetMapping("/social/{source}")
    @PermitAll
    @Operation(summary = "社交登录,获取授权链接")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "获取授权链接", responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "获取授权链接失败", responseCode = "400")
    })
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "source", description = "社交登录类型", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "domain", description = "域名", required = true)
    })
    public R<?> socialLogin(@PathVariable String source, String domain) {
        Optional<AuthRequest> authRequest = socialAuthRequestFactory.get(source);
        if (authRequest.isEmpty()) {
            return R.NG("不支持的登录方式");
        }
        Map<String, String> params = new HashMap<>(2);
        params.put("domain", domain);
        String state = AuthStateUtils.createState();
        params.put("state", state);
        String json = JsonUtil.DEFAULT.toJson(params);
        // 生成授权链接
        String url = authRequest.get().authorize(Base64Util.encode(json, StrPool.CHARSET_UTF_8));
        return R.OK(url);
    }

    /**
     * 社交回调
     *
     * @param source 类型
     * @return 结果
     */
    @PermitAll
    @PostMapping("/social/callback/{source}")
    @Operation(summary = "社交登录回调,绑定社交账号")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "绑定成功", responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "绑定失败", responseCode = "400")
    })
    @Parameter(name = "source", description = "社交登录类型,github,gitee", required = true)
    @OperatorLog(AuthenticationOperatorType.SOCIAL_BIND)
    public R<?> socialCallback(@PathVariable String source, @RequestBody SocialCallbackRequest request) {
        // 获取用户信息
        AuthResponse<AuthUser> authUserAuthResponse = socialAuthRequestFactory.loginAuth(source, request.getCode(), request.getState());
        AuthUser data = authUserAuthResponse.getData();
        if (!authUserAuthResponse.ok()) {
            return R.NG(authUserAuthResponse.getMsg());
        }
        // 绑定社交账号
        Optional<UserInfo> loginUser = SecurityUtils.getLoginUser();
        if (loginUser.isEmpty()) {
            return R.NG("请先登录");
        }
        authUserService.socialRegister(data, loginUser.get());
        return R.OK();
    }

    /**
     * 获取以绑定的社交账号
     *
     * @return 结果
     */
    @GetMapping("/social/bind")
    public R<List<String>> getBindSocials() {
        Optional<String> loginUserId = SecurityUtils.getLoginUserId();
        if (loginUserId.isEmpty()) {
            return R.NG("请先登录");
        }
        List<String> socials = authUserService.getBindSocials(loginUserId.get());
        return R.OK(socials);
    }

    /**
     * 解绑社交账号
     *
     * @param source 类型
     * @return 结果
     */
    @DeleteMapping("/social/unbind/{source}")
    @OperatorLog(AuthenticationOperatorType.SOCIAL_UNBIND)
    public R<?> socialUnbind(@PathVariable String source) {
        Optional<String> loginUserId = SecurityUtils.getLoginUserId();
        if (loginUserId.isEmpty()) {
            return R.NG("请先登录");
        }
        authUserService.socialUnbind(loginUserId.get(), source);
        return R.OK();
    }


    /**
     * 用户登出
     */
    @PermitAll
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    @OperatorLog(AuthenticationOperatorType.LOGOUT)
    public R<String> logout(HttpServletRequest request) {
        // 获取登录 token
        Optional<String> tokenOptional = SecurityUtils.obtainAuthorization(request);
        tokenOptional.ifPresent(tokenGranterBuilder.defaultGranter()::logout);
        return R.OK("登出成功");
    }


    /**
     * 用户登录
     */
    private R<String> loginByMobile(@Validated @RequestBody PhoneLoginRequest request) {
        // 解密
        String iv = request.getCaptchaKey();
        String key = SecureUtil.sha256(request.getCaptchaKey() + request.getTimestamp());
        byte[] _key = HexUtil.decodeHex(key);
        byte[] _iv = iv.getBytes();
        String phone = AesCryptoUtil.decrypt(request.getCaptchaCode(), AesCryptoUtil.mode, _key, _iv);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(phone);
        loginInfo.setPassword(request.getCaptchaCode());
        return tokenGranterBuilder.getGranter(LoginGrantEnums.MOBILE).login(loginInfo);
    }


    private R<String> loginByEmail(EmailLoginRequest request) {
        // 解密
        String iv = request.getCaptchaKey();
        String key = SecureUtil.sha256(request.getCaptchaKey() + request.getTimestamp());
        byte[] _key = HexUtil.decodeHex(key);
        byte[] _iv = iv.getBytes();
        String email = AesCryptoUtil.decrypt(request.getCaptchaCode(), AesCryptoUtil.mode, _key, _iv);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(email);
        loginInfo.setPassword(request.getCaptchaCode());
        return tokenGranterBuilder.getGranter(LoginGrantEnums.EMAIL).login(loginInfo);
    }

    private R<String> loginByCode(CodeLoginRequest request) {
        // 解密
        String iv = request.getCaptchaKey();
        String key = SecureUtil.sha256(request.getCaptchaKey() + request.getTimestamp());
        byte[] _key = HexUtil.decodeHex(key);
        byte[] _iv = iv.getBytes();
        String username = AesCryptoUtil.decrypt(request.getUsername(), AesCryptoUtil.mode, _key, _iv);
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUsername(username);
        loginInfo.setPassword(request.getCaptchaCode());
        // 判断是否是邮箱/手机号
        if (RegexUtil.isMatch(RegexPool.EMAIL, username)) {
            return tokenGranterBuilder.getGranter(LoginGrantEnums.EMAIL).login(loginInfo);
        } else if (RegexUtil.isMatch(RegexPool.MOBILE, username)) {
            return tokenGranterBuilder.getGranter(LoginGrantEnums.MOBILE).login(loginInfo);
        } else {
            return R.NG("暂不支持该登录方式");
        }
    }

    /**
     * 用户登录
     */
    private R<String> loginByUsername(UsernameLoginRequest request) {
        LoginInfo loginInfo = new LoginInfo();
        // 解密
        String iv = request.getCaptchaKey();
        String key = SecureUtil.sha256(request.getCaptchaKey() + request.getTimestamp());
        byte[] _key = HexUtil.decodeHex(key);
        byte[] _iv = iv.getBytes();
        String password = AesCryptoUtil.decrypt(request.getPassword(), AesCryptoUtil.mode, _key, _iv);
        // 解密密码
        loginInfo.setPassword(password);
        loginInfo.setUsername(request.getUsername());
        // 登录
        return tokenGranterBuilder.getGranter(LoginGrantEnums.PASSWORD).login(loginInfo);
    }


    /**
     * 社交登录
     */
    private R<String> loginBySocial(SocialLoginRequest request) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setSocialCode(request.getCode());
        loginInfo.setSocialState(request.getState());
        loginInfo.setSocialSource(request.getSource());
        return tokenGranterBuilder.getGranter(LoginGrantEnums.SOCIAL).login(loginInfo);
    }


}
