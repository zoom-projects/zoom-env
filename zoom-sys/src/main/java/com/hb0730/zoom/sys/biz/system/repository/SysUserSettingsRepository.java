package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.hb0730.zoom.base.sys.system.entity.SysUserSettings;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysUserSettingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysUserSettingsRepository extends CrudRepository<SysUserSettingsMapper, SysUserSettings> {

    /**
     * 根据用户ID查询用户设置
     *
     * @param userId 用户ID
     * @return 用户设置
     */
    public SysUserSettings getByUserId(String userId) {
        return baseMapper.of(
                query ->
                        query.eq(SysUserSettings::getUserId, userId)
        ).one();
    }

    /**
     * 更新消息通知
     *
     * @param messageNotification 消息通知
     * @param userId              用户ID
     * @return 是否成功
     */
    public boolean updateMessageNotificationByUserId(boolean messageNotification, String userId) {

        LambdaUpdateWrapper<SysUserSettings> up = Wrappers.lambdaUpdate(getEntityClass())
                .set(SysUserSettings::getMessageNotification, messageNotification)
                .eq(SysUserSettings::getUserId, userId);
        return baseMapper.update(up) > 0;
    }
}
