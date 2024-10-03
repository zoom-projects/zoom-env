package com.hb0730.zoom.sys.biz.base.granter;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;
import com.hb0730.zoom.sys.biz.base.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 手机号码登录
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Component(MobileTokenGranter.GRANT_TYPE)
@RequiredArgsConstructor
public class MobileTokenGranter extends AbstractTokenGranter {
    public static final String GRANT_TYPE = "mobile";
    private final CaptchaService captchaService;

    @Override
    protected R<String> checkParam(LoginInfo loginInfo) {
        String mobile = loginInfo.getUsername();
        String code = loginInfo.getPassword();
        if (mobile == null || code == null) {
            return R.NG("手机号码或验证码不能为空");
        }
        return R.OK();
    }

    @Override
    protected R<String> checkCaptcha(LoginInfo loginInfo) {
        return captchaService.checkCaptcha(loginInfo.getUsername(), loginInfo.getPassword());
    }

    @Override
    protected R<SysUser> getUser(LoginInfo loginInfo) {
        // TODO 通过手机号码获取用户
        return R.NG("暂时不支持手机号码登录");
    }
}
