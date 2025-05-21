package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.extension.repository.IRepository;
import com.hb0730.zoom.base.core.repository.BasicRepository;
import com.hb0730.zoom.base.sys.system.entity.SysUserSubscribeMsg;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysUserSubscribeMsgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysUserSubscribeMsgRepository extends BasicRepository<String,
        SysUserSubscribeMsg, SysUserSubscribeMsgMapper> {

    /**
     * 通过用户ID获取用户订阅消息
     *
     * @param userId 用户ID
     * @return 用户订阅消息
     */
    public List<SysUserSubscribeMsg> listByUserId(String userId) {
        return lambdaQuery()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .list();
    }

    /**
     * 批量保存用户订阅消息
     *
     * @param list 用户订阅消息列表
     * @return 是否成功
     */
    public boolean saveBatch(List<SysUserSubscribeMsg> list) {
        return saveBatch(list, IRepository.DEFAULT_BATCH_SIZE);
    }

    /**
     * 删除用户订阅消息
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅消息ID列表
     * @return 是否成功
     */
    public boolean deleteByUserIdAndSubscribeIds(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .remove();
    }

    /**
     * email订阅
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @return 是否成功
     */
    public boolean emailSubscribe(String userId, String subscribeId) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .eq(StrUtil.isNotBlank(subscribeId), SysUserSubscribeMsg::getSubscribeId, subscribeId)
                .set(SysUserSubscribeMsg::getIsEmail, true)
                .update();
    }

    /**
     * 批量email订阅
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @return 是否成功
     */
    public boolean emailBatchSubscribe(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(CollectionUtil.isNotEmpty(subscribeIds), SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .set(SysUserSubscribeMsg::getIsEmail, true)
                .update();
    }

    /**
     * 取消email订阅
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @return 是否成功
     */
    public boolean cancelEmailSubscribe(String userId, String subscribeId) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .eq(StrUtil.isNotBlank(subscribeId), SysUserSubscribeMsg::getSubscribeId, subscribeId)
                .set(SysUserSubscribeMsg::getIsEmail, false)
                .update();
    }

    /**
     * 批量取消email订阅
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @return 是否成功
     */
    public boolean cancelEmailBatchSubscribe(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(CollectionUtil.isNotEmpty(subscribeIds), SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .set(SysUserSubscribeMsg::getIsEmail, false)
                .update();
    }

    /**
     * sms订阅
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @return 是否成功
     */
    public boolean smsSubscribe(String userId, String subscribeId) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .eq(StrUtil.isNotBlank(subscribeId), SysUserSubscribeMsg::getSubscribeId, subscribeId)
                .set(SysUserSubscribeMsg::getIsSms, true)
                .update();
    }

    /**
     * 批量sms订阅
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @return 是否成功
     */
    public boolean smsBatchSubscribe(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(CollectionUtil.isNotEmpty(subscribeIds), SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .set(SysUserSubscribeMsg::getIsSms, true)
                .update();
    }

    /**
     * 取消sms订阅
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @return 是否成功
     */
    public boolean cancelSmsSubscribe(String userId, String subscribeId) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .eq(StrUtil.isNotBlank(subscribeId), SysUserSubscribeMsg::getSubscribeId, subscribeId)
                .set(SysUserSubscribeMsg::getIsSms, false)
                .update();
    }

    /**
     * 批量取消sms订阅
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @return 是否成功
     */
    public boolean cancelSmsBatchSubscribe(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(CollectionUtil.isNotEmpty(subscribeIds), SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .set(SysUserSubscribeMsg::getIsSms, false)
                .update();
    }

    /**
     * site订阅
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @return 是否成功
     */
    public boolean siteSubscribe(String userId, String subscribeId) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .eq(StrUtil.isNotBlank(subscribeId), SysUserSubscribeMsg::getSubscribeId, subscribeId)
                .set(SysUserSubscribeMsg::getIsSite, true)
                .update();
    }

    /**
     * 批量site订阅
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @return 是否成功
     */
    public boolean siteBatchSubscribe(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(CollectionUtil.isNotEmpty(subscribeIds), SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .set(SysUserSubscribeMsg::getIsSite, true)
                .update();
    }

    /**
     * 取消site订阅
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @return 是否成功
     */
    public boolean cancelSiteSubscribe(String userId, String subscribeId) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .eq(StrUtil.isNotBlank(subscribeId), SysUserSubscribeMsg::getSubscribeId, subscribeId)
                .set(SysUserSubscribeMsg::getIsSite, false)
                .update();
    }

    /**
     * 批量取消site订阅
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @return 是否成功
     */
    public boolean cancelSiteBatchSubscribe(String userId, List<String> subscribeIds) {
        return lambdaUpdate()
                .eq(SysUserSubscribeMsg::getUserId, userId)
                .in(CollectionUtil.isNotEmpty(subscribeIds), SysUserSubscribeMsg::getSubscribeId, subscribeIds)
                .set(SysUserSubscribeMsg::getIsSite, false)
                .update();
    }
}
