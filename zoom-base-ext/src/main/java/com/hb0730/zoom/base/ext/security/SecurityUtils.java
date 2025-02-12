package com.hb0730.zoom.base.ext.security;

import com.hb0730.zoom.base.AppUtil;
import com.hb0730.zoom.base.ZoomConst;
import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.base.ext.services.proxy.SysProxyService;
import com.hb0730.zoom.base.meta.UserContext;
import com.hb0730.zoom.base.meta.UserInfo;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.cache.core.CacheUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.hb0730.zoom.base.ZoomConst.LOGIN_USER_CACHE_PREFIX;

/**
 * 安全工具类
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取 token
     *
     * @param request request
     * @return token
     */
    public static Optional<String> obtainAuthorization(HttpServletRequest request) {
        String token = request.getHeader(ZoomConst.X_ACCESS_TOKEN);
        token = StrUtil.blankToDefault(token, null);
        return Optional.ofNullable(token).map(String::trim);
    }

    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public static Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication());
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static Optional<UserInfo> getLoginUser() {
        try {
            Optional<UserInfo> userInfo = getAuthentication().map(authentication -> {
                Object principal = authentication.getPrincipal();
                if (principal instanceof UserInfo) {
                    return (UserInfo) principal;
                }
                return null;
            });
            if (userInfo.isPresent()) {
                return userInfo;
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("未认证请求", e);
            }
        }
        UserInfo currentUser = getCurrentUser(UserContext.getCurrentUserName());
        return Optional.ofNullable(currentUser);
    }


    /**
     * 获取当前 userId
     *
     * @return id
     */
    public static Optional<String> getLoginUserId() {
        return getLoginUser().map(UserInfo::getId);
    }

    /**
     * 获取当前 username
     *
     * @return username
     */
    public static Optional<String> getLoginUsername() {
        return getLoginUser().map(UserInfo::getUsername);
    }

    /**
     * 清空用户上下文
     */
    public static void clearAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }


    /**
     * 默认密码加密
     *
     * @return .
     */
    public static PasswordEncoder defaultPasswordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    /**
     * 取得用户信息
     *
     * @param userName 用户账号
     * @return 用户信息
     */
    public static UserInfo getCurrentUser(String userName) {
        if (StrUtil.isBlank(userName)) {
            return null;
        }
        try {
            String jsonStr = CacheUtil.getFromCache(userName,
                    new CacheUtil.CallBack<String, String>(LOGIN_USER_CACHE_PREFIX) {
                        @Override
                        public String getCachedKey(String params) {
                            return params;
                        }

                        @Override
                        public String doCallback(String params) {
                            // 根据住户ID取得租户代码=客户代码
                            UserInfoDTO infoDTO = AppUtil.getBean(SysProxyService.class).findUsername(userName);
                            if (null != infoDTO) {
                                return JsonUtil.DEFAULT.toJson(infoDTO);
                            }
                            return null;
                        }
                    });
            if (StrUtil.isNotBlank(jsonStr)) {
                return JsonUtil.DEFAULT.json2Obj(jsonStr, UserInfo.class);
            }
            return null;
        } catch (Exception e) {
            log.warn("~~~用户[{}]信息加载失败~~~", userName);
        }
        return null;

    }
}
