package com.hb0730.zoom.sys.biz.system.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.util.AesCryptoUtil;
import com.hb0730.zoom.sys.biz.system.mode.request.UserCreateRequest;
import com.hb0730.zoom.sys.biz.system.service.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/25
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户管理")
@Validated
public class SysUserController {
    private final SysUserService service;


    @PostMapping("/add")
    @Schema(description = "添加用户")
    public R<?> createUser(@RequestBody UserCreateRequest request) {
        //加密密码
        request.setPassword(AesCryptoUtil.decrypt(request.getPassword(), request.getCaptchaKey()));
        service.createUser(request);
        return R.OK("添加成功");
    }
}
