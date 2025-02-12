package com.hb0730.zoom.base.ext.security;

import com.hb0730.zoom.base.meta.ICurrentUserService;
import com.hb0730.zoom.base.meta.UserInfo;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/2/12
 */
@Component
public class CurrentUserService implements ICurrentUserService {
    @Override
    public UserInfo getCurrentUser() {
        return SecurityUtils.getLoginUser().orElseGet(UserInfo::new);
    }
}
