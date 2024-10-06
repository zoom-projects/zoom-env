package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.LoginGrantEnums;
import com.hb0730.zoom.base.security.SecurityUtils;
import com.hb0730.zoom.base.utils.AesCryptoUtil;
import com.hb0730.zoom.base.utils.HexUtil;
import com.hb0730.zoom.base.utils.SecureUtil;
import com.hb0730.zoom.sys.biz.base.granter.TokenGranterBuilder;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.base.model.request.PhoneLoginRequest;
import com.hb0730.zoom.sys.biz.base.model.request.UsernameLoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 用户登录
     */
    @PermitAll
    @PostMapping("/login/{type}")
    @Operation(summary = "用户登录,根据不同类型登录，password:用户名密码登录，mobile:手机号登录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "登录成功,返回token", responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "登录失败", responseCode = "400")
    })
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
                                            name = "mobile",
                                            description = "手机登录",
                                            value = "{\"phone\":\"123456\",\"captchaKey\":\"123456\",\"timestamp\":\"123456\"}"
                                    )
                            }
                    )
            }
    )
    public R<String> login(@PathVariable String type,
                           @RequestBody Map<String, String> body) {
        if ("password".equals(type)) {
            UsernameLoginRequest dto = UsernameLoginRequest.of(body);
            return loginByUsername(dto);
        } else if ("mobile".equals(type)) {
            PhoneLoginRequest dto = PhoneLoginRequest.of(body);
            return loginByMobile(dto);
        }

        return R.NG("登录失败，暂未实现");
    }


    /**
     * 用户登录
     */
    public R<String> loginByMobile(@Validated @RequestBody PhoneLoginRequest request) {
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

    /**
     * 用户登录
     */
    public R<String> loginByUsername(UsernameLoginRequest request) {
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
     * 用户登出
     */
    @PermitAll
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public R<String> logout(HttpServletRequest request) {
        // 获取登录 token
        Optional<String> tokenOptional = SecurityUtils.obtainAuthorization(request);
        tokenOptional.ifPresent(tokenGranterBuilder.defaultGranter()::logout);
        return R.OK("登出成功");
    }

}
