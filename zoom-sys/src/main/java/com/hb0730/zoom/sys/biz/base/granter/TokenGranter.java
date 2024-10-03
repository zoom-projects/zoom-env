package com.hb0730.zoom.sys.biz.base.granter;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.sys.biz.base.model.dto.LoginInfo;

/**
 * 授予token接口
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
public interface TokenGranter {
    /**
     * 登录
     *
     * @param loginInfo 登录信息
     * @return token
     */
    R<String> login(LoginInfo loginInfo);


    /**
     * 退出
     *
     * @param token token
     * @return 是否成功
     */
    R<String> logout(String token);
}
