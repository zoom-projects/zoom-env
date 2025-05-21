package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.core.service.BasicService;
import com.hb0730.zoom.base.sys.system.entity.SysUserSubscribeMsg;
import com.hb0730.zoom.sys.biz.base.model.request.UserSubscribeMsgUpdateRequest;
import com.hb0730.zoom.sys.biz.system.repository.SysUserSubscribeMsgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserSubscribeMsgService extends BasicService<String, SysUserSubscribeMsg, SysUserSubscribeMsgRepository> {
    private final SysUserSubscribeMsgRepository repository;

    /**
     * 通过用户ID获取用户订阅消息
     *
     * @param userId 用户ID
     * @return 用户订阅消息
     */
    public List<SysUserSubscribeMsg> listByUserId(String userId) {
        return repository.listByUserId(userId);
    }


    /**
     * 批量保存用户订阅消息
     *
     * @param list 用户订阅消息列表
     * @return 是否成功
     */
    public boolean saveBatch(List<SysUserSubscribeMsg> list) {
        return repository.saveBatch(list);
    }

    /**
     * 删除用户订阅消息
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅消息ID列表
     * @return 是否成功
     */
    public boolean deleteByUserIdAndSubscribeIds(String userId, List<String> subscribeIds) {
        return repository.deleteByUserIdAndSubscribeIds(userId, subscribeIds);
    }

    /**
     * 订阅消息
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @param msgType     消息类型
     * @return 是否成功
     */
    public boolean subscribe(String userId, String subscribeId, UserSubscribeMsgUpdateRequest.MsgType msgType) {
        switch (msgType) {
            case EMAIL -> {
                return repository.emailSubscribe(userId, subscribeId);
            }
            case SMS -> {
                return repository.smsSubscribe(userId, subscribeId);
            }
            case SITE -> {
                return repository.siteSubscribe(userId, subscribeId);
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * 批量订阅消息
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @param msgType      消息类型
     * @return 是否成功
     */
    public boolean batchSubscribe(String userId, List<String> subscribeIds, UserSubscribeMsgUpdateRequest.MsgType msgType) {
        switch (msgType) {
            case EMAIL -> {
                return repository.emailBatchSubscribe(userId, subscribeIds);
            }
            case SMS -> {
                return repository.smsBatchSubscribe(userId, subscribeIds);
            }
            case SITE -> {
                return repository.siteBatchSubscribe(userId, subscribeIds);
            }
            default -> {
                return false;
            }
        }

    }

    /**
     * 取消订阅消息
     *
     * @param userId      用户ID
     * @param subscribeId 订阅ID
     * @param msgType     消息类型
     * @return 是否成功
     */
    public boolean cancelSubscribe(String userId, String subscribeId, UserSubscribeMsgUpdateRequest.MsgType msgType) {
        switch (msgType) {
            case EMAIL -> {
                return repository.cancelEmailSubscribe(userId, subscribeId);
            }
            case SMS -> {
                return repository.cancelSmsSubscribe(userId, subscribeId);
            }
            case SITE -> {
                return repository.cancelSiteSubscribe(userId, subscribeId);
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * 批量取消订阅消息
     *
     * @param userId       用户ID
     * @param subscribeIds 订阅ID列表
     * @param msgType      消息类型
     * @return 是否成功
     */
    public boolean batchCancelSubscribe(String userId, List<String> subscribeIds, UserSubscribeMsgUpdateRequest.MsgType msgType) {
        switch (msgType) {
            case EMAIL -> {
                return repository.cancelEmailBatchSubscribe(userId, subscribeIds);
            }
            case SMS -> {
                return repository.cancelSmsBatchSubscribe(userId, subscribeIds);
            }
            case SITE -> {
                return repository.cancelSiteBatchSubscribe(userId, subscribeIds);
            }
            default -> {
                return false;
            }
        }
    }

}
