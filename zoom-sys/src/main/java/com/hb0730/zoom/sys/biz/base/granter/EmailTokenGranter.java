package com.hb0730.zoom.sys.biz.base.granter;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.mybatis.core.encrypt.MybatisEncryptService;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.base.register.RegistryType;
import com.hb0730.zoom.sys.biz.base.register.UserRegistryFactory;
import com.hb0730.zoom.sys.biz.base.service.CaptchaService;
import com.hb0730.zoom.sys.biz.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 邮箱授权
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/19
 */
@Component(EmailTokenGranter.GRANT_TYPE)
@RequiredArgsConstructor
public class EmailTokenGranter extends AbstractTokenGranter {
    public static final String GRANT_TYPE = "email";
    private final CaptchaService captchaService;
    private final SysUserService userService;
    private final MybatisEncryptService encryptService;
    private final UserRegistryFactory userRegistryFactory;

    @Override
    protected R<String> checkParam(LoginInfo loginInfo) {
        String email = loginInfo.getUsername();
        String code = loginInfo.getPassword();
        if (email == null || code == null) {
            return R.NG("邮箱或验证码不能为空");
        }
        return R.OK();
    }

    @Override
    protected R<String> checkCaptcha(LoginInfo loginInfo) {
        return captchaService.checkCaptcha(loginInfo.getUsername(), loginInfo.getPassword());
    }

    @Override
    protected Optional<SysUser> getUser(LoginInfo loginInfo) {
        String email = encryptService.encrypt(loginInfo.getUsername());
//        return Optional.ofNullable(userService.findByEmail(email));
        SysUser user = userService.findByEmail(email);
        if (null == user) {
            // 开始注册
            user = userRegistryFactory.getRegistry(RegistryType.EMAIL).register(loginInfo.getUsername());
        }
        return Optional.ofNullable(user);
    }
}
