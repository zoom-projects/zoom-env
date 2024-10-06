package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.security.SecurityUtils;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.base.model.vo.PermissionVO;
import com.hb0730.zoom.sys.biz.base.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 用户权限
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@RestController
@RequestMapping("/auth")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "认证服务")
public class PermissionController {
    private final PermissionService permissionService;

    /**
     * 获取用户权限
     *
     * @return 用户权限
     */
    @GetMapping("/menu")
    @Operation(summary = "获取当前用户权限")
    public R<PermissionVO> getPermission(HttpServletRequest request) {
        // 获取登录 token
        Optional<String> tokenOptional = SecurityUtils.obtainAuthorization(request);
        if (tokenOptional.isEmpty()) {
            return R.NG("获取权限失败,token为空");
        }
        String token = tokenOptional.get();
        PermissionVO permission = permissionService.getPermissionByToken(token);
        return R.OK(permission);
    }

    /**
     * 根据token获取用户权限
     *
     * @param token token
     * @return 用户权限
     */
    @GetMapping("/menu/token")
    @Operation(summary = "根据token获取用户权限")
    public R<PermissionVO> getPermissionByToken(String token) {
        if (StrUtil.isBlank(token)) {
            return R.NG("获取权限失败,token为空");
        }
        PermissionVO permission = permissionService.getPermissionByToken(token);
        return R.OK(permission);
    }
}
