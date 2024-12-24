package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.ext.security.SecurityUtils;
import com.hb0730.zoom.base.sys.system.entity.SysUserSettings;
import com.hb0730.zoom.base.utils.AesCryptoUtil;
import com.hb0730.zoom.base.utils.HexUtil;
import com.hb0730.zoom.base.utils.SecureUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.sys.biz.base.convert.UserSettingsConvert;
import com.hb0730.zoom.sys.biz.base.granter.TokenGranterBuilder;
import com.hb0730.zoom.sys.biz.base.model.request.RestEmailOrPhoneRequest;
import com.hb0730.zoom.sys.biz.base.model.request.RestPasswordRequest;
import com.hb0730.zoom.sys.biz.base.model.vo.UserInfoVO;
import com.hb0730.zoom.sys.biz.base.model.vo.UserSettingsVO;
import com.hb0730.zoom.sys.biz.base.service.AuthUserService;
import com.hb0730.zoom.sys.biz.system.model.request.operator.log.SysOperatorLogQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.user.SysUserAccessTokenQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOperatorLogVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysUserAccessTokenVO;
import com.hb0730.zoom.sys.biz.system.service.SysOperatorLogService;
import com.hb0730.zoom.sys.biz.system.service.SysUserAccessTokenService;
import com.hb0730.zoom.sys.biz.system.service.SysUserSettingsService;
import com.hb0730.zoom.sys.define.operator.AuthenticationOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@Tag(name = "认证服务")
public class AuthUserController {
    private final TokenGranterBuilder tokenGranterBuilder;
    private final AuthUserService authUserService;
    private final SysOperatorLogService operatorLogService;
    private final SysUserAccessTokenService sysUserAccessTokenService;
    private final SysUserSettingsService sysUserSettingsService;
    private final UserSettingsConvert userSettingsConvert;


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
    @Operation(summary = "重置当前用户密码")
    @PutMapping("/reset_password")
    @OperatorLog(AuthenticationOperatorType.UPDATE_PASSWORD)
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

    /**
     * 重置邮箱或手机号
     */
    @PutMapping("/reset/{key}")
    @Operation(summary = "重置邮箱或手机号")
    @OperatorLog(AuthenticationOperatorType.UPDATE_EMAIL_PHONE)
    public R<String> restEmailOrPhone(@PathVariable String key,
                                      @Validated @RequestBody RestEmailOrPhoneRequest request) {
        // 解密
        String iv = request.getCaptchaKey();
        String key1 = SecureUtil.sha256(request.getCaptchaKey() + request.getTimestamp());
        byte[] _key = HexUtil.decodeHex(key1);
        byte[] _iv = iv.getBytes();
        String emailOrPhone = AesCryptoUtil.decrypt(request.getKey(), AesCryptoUtil.mode, _key, _iv);
        request.setKey(emailOrPhone);
        request.setOperatorId(SecurityUtils.getLoginUserId().orElse(null));
        return switch (key.toLowerCase()) {
            case "email" -> authUserService.resetEmail(request);
            case "phone" -> authUserService.resetPhone(request);
            default -> R.NG("不支持的操作");
        };
    }

    @Operation(summary = "查询操作日志")
    @GetMapping("/operator_log/page")
    public R<Page<SysOperatorLogVO>> queryOperatorLog(SysOperatorLogQueryRequest request) {
        SecurityUtils.getLoginUserId().ifPresent(request::setOperatorId);
        if (StrUtil.isBlank(request.getOperatorId())) {
            return R.NG("获取用户信息失败,token为空");
        }
        Page<SysOperatorLogVO> page = operatorLogService.page(request);
        return R.OK(page);
    }

    /**
     * 查询用户访问令牌
     */
    @Operation(summary = "查询用户访问令牌")
    @GetMapping("/access_token")
    public R<Page<SysUserAccessTokenVO>> queryAccessToken(PageRequest request) {
        SysUserAccessTokenQueryRequest queryRequest = SysUserAccessTokenQueryRequest.of(request);
        SecurityUtils.getLoginUserId().ifPresent(queryRequest::setUserId);
        if (StrUtil.isBlank(queryRequest.getUserId())) {
            return R.NG("获取用户信息失败,token为空");
        }
        Page<SysUserAccessTokenVO> page = sysUserAccessTokenService.page(queryRequest);
        return R.OK(page);
    }

    @PostMapping("/access_token")
    @Operation(summary = "保存用户访问令牌")
    @OperatorLog(AuthenticationOperatorType.CREATE_ACCESS_TOKEN)
    public R<String> saveAccessToken(@RequestBody SysUserAccessTokenCreateRequest request) {
        if (SecurityUtils.getLoginUserId().isEmpty()) {
            return R.NG("获取用户信息失败,token为空");
        }
        String userId = SecurityUtils.getLoginUserId().get();
        String accessToken = sysUserAccessTokenService.createAccessToken(userId, request);
        return R.OK(accessToken);
    }

    /**
     * 撤销用户访问令牌
     */
    @Operation(summary = "撤销用户访问令牌")
    @PutMapping("/access_token/cancel/{id}")
    @OperatorLog(AuthenticationOperatorType.CANCEL_ACCESS_TOKEN)
    public R<String> cancelAccessToken(@PathVariable String id) {
        boolean result = sysUserAccessTokenService.cancelAccessToken(id);
        return result ? R.OK() : R.NG("撤销失败");
    }

    /**
     * 恢复用户访问令牌
     */
    @Operation(summary = "恢复用户访问令牌")
    @PutMapping("/access_token/restore/{id}")
    @OperatorLog(AuthenticationOperatorType.RESTORE_ACCESS_TOKEN)
    public R<String> restoreAccessToken(@PathVariable String id) {
        boolean result = sysUserAccessTokenService.resumeAccessToken(id);
        return result ? R.OK() : R.NG("恢复失败");
    }

    /**
     * 删除用户访问令牌
     */
    @Operation(summary = "删除用户访问令牌")
    @DeleteMapping("/access_token/delete/{id}")
    @OperatorLog(AuthenticationOperatorType.DELETE_ACCESS_TOKEN)
    public R<String> deleteAccessToken(@PathVariable String id) {
        boolean result = sysUserAccessTokenService.deleteById(id);
        return result ? R.OK() : R.NG("删除失败");
    }

    @Operation(summary = "获取用户设置")
    @GetMapping("/settings")
    public R<UserSettingsVO> getUserSettings(HttpServletRequest request) {
        String userId = SecurityUtils.getLoginUserId().orElse(null);
        if (StrUtil.isBlank(userId)) {
            return R.NG("获取用户信息失败,token为空");
        }
        SysUserSettings userSettings = sysUserSettingsService.findByUserId(userId);
        UserSettingsVO res = userSettingsConvert.toObject(userSettings);
        return R.OK(res);
    }

    @Operation(summary = "保存用户设置")
    @PutMapping("/settings")
    @OperatorLog(AuthenticationOperatorType.UPDATE_USER_SETTINGS)
    public R<String> userSettings(HttpServletRequest request, @RequestBody UserSettingsVO userSettingsVO) {
        String userId = SecurityUtils.getLoginUserId().orElse(null);
        if (StrUtil.isBlank(userId)) {
            return R.NG("获取用户信息失败,token为空");
        }
        SysUserSettings userSettings = userSettingsConvert.toEntity(userSettingsVO);
        userSettings.setUserId(userId);
        sysUserSettingsService.saveOrUpdate(userSettings);
        return R.OK("更新成功");
    }

}
