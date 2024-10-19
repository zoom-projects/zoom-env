package com.hb0730.zoom.base.sys.notifty.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.notifty.entity.SysMessageTemplate;
import com.hb0730.zoom.base.sys.notifty.mapper.MessageTemplateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Service
@Slf4j
public class MessageTemplateService extends ServiceImpl<MessageTemplateMapper, SysMessageTemplate> {

    /**
     * 根据消息模板代码取得消息模板
     *
     * @param code 消息模板代码
     * @return 消息模板
     */
    public SysMessageTemplate selectByCode(String code) {
        LambdaQueryWrapper<SysMessageTemplate> queryWrapper = Wrappers.lambdaQuery(SysMessageTemplate.class)
                .eq(SysMessageTemplate::getTemplateCode, code)
                .eq(SysMessageTemplate::getStatus, 1);
        return getOne(queryWrapper, false);
    }
}
