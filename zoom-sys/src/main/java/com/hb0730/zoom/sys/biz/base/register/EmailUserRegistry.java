package com.hb0730.zoom.sys.biz.base.register;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.CaptchaTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.sys.message.service.MessageService;
import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.base.utils.PasswdUtil;
import com.hb0730.zoom.sys.biz.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 邮件注册
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/2/25
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailUserRegistry implements UserRegistry {
    private final SysUserService sysUserService;
    private final MessageService messageService;

    @Override
    public SysUser register(String username) {
        log.info("邮箱注册：{}", username);
        String password = PasswdUtil.generatePwd(6);
        R<SysUser> res = sysUserService.createByEmail(username, password);
        if (!res.isSuccess()) {
            log.error("邮箱注册失败：{}", res.getMessage());
            return null;
        }
        // 发送邮件
        SaveMessageDTO dto = new SaveMessageDTO();
        dto.setReceiver(username);
        String code = CaptchaTypeEnums.REGISTRY_LOGIN_EMAIL.getCode();
        dto.setTemplateCode(code);
        dto.setMsgType(CaptchaTypeEnums.REGISTRY_LOGIN_EMAIL.getMsgType());
        Map<String, String> extra = Map.of(
                "user_email", username,
                "random_password", password);
        dto.setExtra(extra);
        messageService.saveMessage(dto);
        return res.getData();
    }

    @Override
    public RegistryType getType() {
        return RegistryType.EMAIL;
    }
}
