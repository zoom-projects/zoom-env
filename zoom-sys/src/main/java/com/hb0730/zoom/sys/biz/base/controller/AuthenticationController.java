package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.util.AesCryptoUtil;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.base.model.request.LoginRequest;
import com.hb0730.zoom.sys.biz.base.service.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@RestController
@RequestMapping("/sys/auth")
@Tag(name = "认证服务")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationServiceImpl service;

    /**
     * 用户登录
     */
    @PermitAll
    @PostMapping("/login")
    @Operation(summary = "用户登录", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "登录成功,返回token", responseCode = "200"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(description = "登录失败", responseCode = "400")
    })
    public R<String> login(@RequestBody LoginRequest request) {
        LoginInfo loginInfo = new LoginInfo();
        // 解密密码
        loginInfo.setUsername(request.getUsername());
        loginInfo.setPassword(AesCryptoUtil.decrypt(request.getPassword(), request.getCaptchaKey()));

        // 登录
        return service.login(loginInfo);
    }

    /**
     * 用户登出
     */
    @PermitAll
    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public String logout(HttpServletRequest request) {
        throw new UnsupportedOperationException("未实现");
    }
}
