package com.hb0730.zoom.sys.biz.base.granter;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.SocialTypeEnums;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.sys.system.entity.SysUserSocial;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.social.core.SocialAuthRequestFactory;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysUserSocialMapper;
import com.hb0730.zoom.sys.biz.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 社交登录
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/18
 */
@Component(SocialTokenGranter.GRANT_TYPE)
@RequiredArgsConstructor
@Slf4j
public class SocialTokenGranter extends AbstractTokenGranter {
    public static final String GRANT_TYPE = "social";
    private final SocialAuthRequestFactory socialAuthRequestFactory;
    private final SysUserSocialMapper sysUserSocialMapper;
    private final SysUserService userService;

    @Override
    protected R<String> checkParam(LoginInfo loginInfo) {
        if (loginInfo.getSocialSource() == null
                || loginInfo.getSocialCode() == null
                || loginInfo.getSocialState() == null) {
            return R.NG("社交登录参数不能为空");
        }
        String socialCode = loginInfo.getSocialCode();
        Optional<SocialTypeEnums> socialTypeEnums = PairEnum.of(SocialTypeEnums.class, loginInfo.getSocialSource());
        if (socialTypeEnums.isEmpty()) {
            return R.NG("不支持的社交登录类型:" + loginInfo.getSocialSource());
        }
        return R.OK();
    }

    @Override
    protected Optional<SysUser> getUser(LoginInfo loginInfo) {
        AuthResponse<AuthUser> authUserAuthResponse = socialAuthRequestFactory.loginAuth(loginInfo.getSocialSource(),
                loginInfo.getSocialCode(),
                loginInfo.getSocialState());
        AuthUser authUser = authUserAuthResponse.getData();
        if (!authUserAuthResponse.ok()
                || authUser == null) {
            log.error("社交登录失败:{}", authUserAuthResponse.getMsg());
            return Optional.empty();
        }
        // authId
        String authId = authUser.getSource().toLowerCase() + authUser.getUuid();
        List<SysUserSocial> userSocials = sysUserSocialMapper.of(Wrappers.lambdaQuery(SysUserSocial.class)
                        .eq(SysUserSocial::getAuthId, authId))
                .list();
        if (CollectionUtil.isEmpty(userSocials)) {
            return Optional.empty();
        }
        SysUserSocial userSocial = userSocials.get(0);
        String userId = userSocial.getUserId();
        return Optional.ofNullable(userService.getById(userId));
    }
}
