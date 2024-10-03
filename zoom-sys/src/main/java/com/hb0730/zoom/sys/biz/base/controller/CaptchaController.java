package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.sys.biz.base.service.CaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 验证码服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@RestController
@RequestMapping("/sys/captcha")
@Tag(name = "验证码服务")
@Slf4j
@RequiredArgsConstructor
public class CaptchaController {
    private final CaptchaService captchaService;


    /**
     * 发送验证码
     */
    @PostMapping("/send/{type}")
    @Operation(summary = "发送验证码", description = "根据不同类型发送验证码: ")
    @PermitAll
    public R<String> sendCaptcha(@PathVariable String type,
                                 @RequestBody Map<String, String> params) {
        return null;
    }
}
