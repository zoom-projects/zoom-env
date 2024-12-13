package com.hb0730.zoom.base.sys.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysUserSettings;
import com.hb0730.zoom.base.sys.system.mapper.UserSettingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/13
 */
@Service
@Slf4j
public class UserSettingsService extends ServiceImpl<UserSettingsMapper, SysUserSettings> {

    /**
     * 根据用户ID查询用户设置
     *
     * @param userId 用户ID
     * @return 用户设置
     */
    public SysUserSettings findByUserId(String userId) {
        LambdaQueryWrapper<SysUserSettings> queryWrapper = Wrappers.lambdaQuery(SysUserSettings.class)
                .eq(SysUserSettings::getUserId, userId);
        return getOne(queryWrapper, false);
    }
}
