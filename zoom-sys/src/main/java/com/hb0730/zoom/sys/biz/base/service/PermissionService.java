package com.hb0730.zoom.sys.biz.base.service;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.convert.Convert;
import com.hb0730.zoom.base.Pair;
import com.hb0730.zoom.base.enums.MenuTypeEnums;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.sys.system.entity.SysPermission;
import com.hb0730.zoom.base.utils.AesCryptoUtil;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.base.convert.PermissionCovert;
import com.hb0730.zoom.sys.biz.base.model.vo.PermissionVO;
import com.hb0730.zoom.sys.biz.base.model.vo.RouteVO;
import com.hb0730.zoom.sys.biz.base.util.RouteUtil;
import com.hb0730.zoom.sys.biz.system.service.SysPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {
    private final SysPermissionService permissionService;
    private final PermissionCovert permissionCovert;

    /**
     * 根据token获取用户权限
     *
     * @param token token
     * @return 用户权限
     */
    public PermissionVO getPermissionByToken(String token) {
        Pair<String, Long> pair = parseToken(token);
        if (null == pair) {
            throw new ZoomException("非法token，解析失败");
        }
        String userId = pair.getCode();
        return getPermissionByUserId(userId);
    }

    /**
     * 根据用户id获取用户权限
     *
     * @param userId 用户id
     * @return 用户权限
     */
    public PermissionVO getPermissionByUserId(String userId) {
        List<SysPermission> sysPermissions = permissionService.findByUserId(userId);
        if (CollectionUtil.isEmpty(sysPermissions)) {
            return null;
        }
        // 过滤掉禁用的
        sysPermissions.removeIf(SysPermission::isDisabled);
        if (CollectionUtil.isEmpty(sysPermissions)) {
            return null;
        }

        // 获取button
        List<SysPermission> buttons = CollectionUtil.filter(sysPermissions,
                sysPermission -> MenuTypeEnums.BUTTON.getCode().equals(sysPermission.getMenuType()));

        List<String> btn = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(buttons)) {
            buttons.stream().map(SysPermission::getPerms).forEach(btn::add);
        }

        List<RouteVO> routes = RouteUtil.buildRoutes(permissionCovert.entityListToDtoList(sysPermissions));
        PermissionVO permissionVO = new PermissionVO();
        permissionVO.setMenu(routes);
        permissionVO.setAuth(btn);
        return permissionVO;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return id, loginTime
     */
    private Pair<String, Long> parseToken(String token) {
        try {
            if (StrUtil.isBlank(token)) {
                return null;
            }
            String decrypt = AesCryptoUtil.decrypt(Base62.decodeStr(token));
            String[] split = decrypt.split(":");
            return new Pair<>(split[0], Convert.toLong(split[1]));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
