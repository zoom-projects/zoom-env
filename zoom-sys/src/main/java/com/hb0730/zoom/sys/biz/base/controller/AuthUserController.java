package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.security.SecurityUtils;
import com.hb0730.zoom.base.utils.AesCryptoUtil;
import com.hb0730.zoom.base.utils.HexUtil;
import com.hb0730.zoom.base.utils.SecureUtil;
import com.hb0730.zoom.sys.biz.base.granter.TokenGranterBuilder;
import com.hb0730.zoom.sys.biz.base.model.request.RestPasswordRequest;
import com.hb0730.zoom.sys.biz.base.model.vo.UserInfoVO;
import com.hb0730.zoom.sys.biz.base.service.AuthUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 用户认证
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/9
 */
@RestController
@RequestMapping("/auth/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AuthUserController {
    private final TokenGranterBuilder tokenGranterBuilder;
    private final AuthUserService authUserService;


    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取当前用户信息")
    @GetMapping("")
    public R<UserInfoVO> currentUser(HttpServletRequest request) {
        // 获取登录 token
        Optional<String> tokenOptional = SecurityUtils.obtainAuthorization(request);
        if (tokenOptional.isEmpty()) {
            return R.NG("获取用户信息失败,token为空");
        }
        String token = tokenOptional.get();
        return tokenGranterBuilder.defaultGranter().currentUser(token);
    }

    /**
     * 重置密码
     */
    @Operation(summary = "重置密码")
    @PutMapping("/reset_password")
    public R<String> resetPassword(HttpServletRequest request,
                                   @RequestBody RestPasswordRequest data) {

        // 解密
        String iv = data.getCaptchaKey();
        String key = SecureUtil.sha256(data.getCaptchaKey() + data.getTimestamp());
        byte[] _key = HexUtil.decodeHex(key);
        byte[] _iv = iv.getBytes();
        // 解密旧密码
        String oldPassword = AesCryptoUtil.decrypt(data.getOldPassword(), AesCryptoUtil.mode, _key, _iv);
        // 解密新密码
        String newPassword = AesCryptoUtil.decrypt(data.getNewPassword(), AesCryptoUtil.mode, _key, _iv);
        data.setOldPassword(oldPassword);
        data.setNewPassword(newPassword);
        // 获取登录 token
        Optional<String> tokenOptional = SecurityUtils.obtainAuthorization(request);
        if (tokenOptional.isEmpty()) {
            return R.NG("获取用户信息失败,token为空");
        }
        String token = tokenOptional.get();
        // 重置密码
        return authUserService.restPassword(token, data);
    }
}
