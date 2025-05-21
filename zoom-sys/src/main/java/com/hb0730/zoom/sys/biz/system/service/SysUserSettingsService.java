package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.sys.system.entity.SysUserSettings;
import com.hb0730.zoom.sys.biz.system.repository.SysUserSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户设置
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/11
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserSettingsService {
    private final SysUserSettingsRepository repository;

    /**
     * 通过用户ID获取用户设置
     *
     * @param userId 用户ID
     * @return 用户设置
     */
    public SysUserSettings findByUserId(String userId) {
        return repository.getByUserId(userId);
    }

    /**
     * 更新消息通知
     *
     * @param userId              用户ID
     * @param messageNotification 消息通知
     * @return 是否成功
     */
    public boolean updateMessageNotification(String userId, boolean messageNotification) {
//        SysUserSettings sysUserSettings = new SysUserSettings();
//        sysUserSettings.setMessageNotification(messageNotification);
//        LambdaQueryWrapper<SysUserSettings> queryWrapper = Wrappers.lambdaQuery(SysUserSettings.class)
//                .eq(SysUserSettings::getUserId, userId);
//        return update(sysUserSettings, queryWrapper);
        return repository.updateMessageNotificationByUserId(messageNotification, userId);
    }

    /**
     * 更新或保存用户设置
     *
     * @param sysUserSettings 用户设置
     * @return 是否成功
     */
    public boolean saveOrUpdate(SysUserSettings sysUserSettings) {
        return repository.saveOrUpdate(sysUserSettings);
    }

}
