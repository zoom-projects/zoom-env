package com.hb0730.zoom.base.sys.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.sys.system.entity.SysUserSubscribeMsg;
import com.hb0730.zoom.base.sys.system.mapper.UserSubscribeMsgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.hb0730.zoom.base.ZoomConst.USER_SUBSCRIBE_MSG_CACHE_KEY;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Service
@Slf4j
public class UserSubscribeMsgService extends ServiceImpl<UserSubscribeMsgMapper, SysUserSubscribeMsg> {

    /**
     * 检查用户是否订阅
     *
     * @param userId  用户ID
     * @param code    代码
     * @param msgType 消息类型
     * @return 是否订阅
     */
    @Cacheable(value = USER_SUBSCRIBE_MSG_CACHE_KEY, key = "#userId+':'+#code+':'+#msgType", unless = "#result == null")
    public boolean checkSubscribe(String userId, String code, MessageTypeEnums msgType) {
        return findUserSubscribe(userId, code, msgType);
    }


    /**
     * 查询用户订阅
     *
     * @param userId  用户ID
     * @param code    代码
     * @param msgType 消息类型
     * @return 是否订阅
     */
    @Cacheable(value = USER_SUBSCRIBE_MSG_CACHE_KEY, key = "#userId+':'+#code+':'+#msgType", unless = "#result == null")
    public Boolean findUserSubscribe(String userId, String code, MessageTypeEnums msgType) {
        SysUserSubscribeMsg userSubscribe = baseMapper.findUserSubscribe(userId, code);
        if (null == userSubscribe) {
            return false;
        }
        return switch (msgType) {
            case EMAIL -> userSubscribe.getIsEmail();
            case SMS -> userSubscribe.getIsSms();
            case SITE -> userSubscribe.getIsSite();
            default -> false;
        };
    }
}
