package com.hb0730.zoom.sys.biz.base.service;

import com.hb0730.zoom.base.sys.message.entity.SysMessageSubscribe;
import com.hb0730.zoom.base.sys.system.entity.SysUserSubscribeMsg;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.base.convert.UserSubscribeMsgConvert;
import com.hb0730.zoom.sys.biz.base.model.request.UserSubscribeMsgUpdateRequest;
import com.hb0730.zoom.sys.biz.base.model.vo.UserSubscribeMsgVO;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeGroupVO;
import com.hb0730.zoom.sys.biz.system.service.SysMessageSubscribeService;
import com.hb0730.zoom.sys.biz.system.service.SysUserSubscribeMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserSubscribeMsgService {
    private final SysMessageSubscribeService sysMessageSubscribeService;
    private final SysUserSubscribeMsgService sysUserSubscribeMsgService;
    private final UserSubscribeMsgConvert userSubscribeMsgConvert;


    /**
     * 通过用户ID获取订阅消息
     *
     * @param userId 用户ID
     * @return 订阅消息
     */
    public List<UserSubscribeMsgVO> getUserSubscribeMsg(String userId) {
        List<SysMessageSubscribeGroupVO> group = sysMessageSubscribeService.group(new SysMessageSubscribeQueryRequest());
        List<UserSubscribeMsgVO> res = userSubscribeMsgConvert.toObjectList(group);
        List<SysUserSubscribeMsg> list = list(userId);
        // 设置订阅状态 站内消息，邮件，短信
        if (CollectionUtil.isEmpty(list)) {
            return res;
        }
        Map<String, SysUserSubscribeMsg> subscribed = list.stream().collect(Collectors.toMap(SysUserSubscribeMsg::getSubscribeId, Function.identity()));

        res.forEach(r -> {
            List<UserSubscribeMsgVO.UserSubscribeMsgChildVO> children = r.getChildren();
            if (CollectionUtil.isEmpty(children)) {
                return;
            }
            children.forEach(c -> {
                SysUserSubscribeMsg sysUserSubscribeMsg = subscribed.get(c.getId());
                if (sysUserSubscribeMsg != null) {
                    c.setIsEmail(sysUserSubscribeMsg.getIsEmail());
                    c.setIsSms(sysUserSubscribeMsg.getIsSms());
                    c.setIsSite(sysUserSubscribeMsg.getIsSite());

                }
            });
            // 设置订阅状态
            // 子类全部订阅
            if (children.stream().allMatch(UserSubscribeMsgVO.UserSubscribeMsgChildVO::getIsEmail)) {
                r.setIsEmail(true);
            }
            if (children.stream().allMatch(UserSubscribeMsgVO.UserSubscribeMsgChildVO::getIsSms)) {
                r.setIsSms(true);
            }
            if (children.stream().allMatch(UserSubscribeMsgVO.UserSubscribeMsgChildVO::getIsSite)) {
                r.setIsSite(true);
            }
        });
        return res;
    }


    /**
     * 保存用户订阅消息
     *
     * @param userId  用户ID
     * @param request 请求
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUserSubscribeMsg(String userId, UserSubscribeMsgUpdateRequest request) {
        // 先同步订阅消息，保证订阅消息的完整性
        sync(userId);
        // 是否取消
        boolean isCancel = Boolean.FALSE.equals(request.getValue());
        boolean isBatch = StrUtil.isBlank(request.getId());
        // 批量取消
        if (isCancel & isBatch) {
            // 判断是否是模块
            if (StrUtil.isNotBlank(request.getName())) {
                return batchCancel(userId, request.getName(), request.getType());
            }
            return batchCancel(userId, request.getType());
        }
        // 单个取消
        if (isCancel & !isBatch) {
            return cancel(userId, request.getId(), request.getType());
        }
        // 单个订阅
        if (!isBatch) {
            return subscribe(userId, request.getId(), request.getType());
        }
        // 批量订阅
        // 判断是否是模块
        if (StrUtil.isNotBlank(request.getName())) {
            return batchSubscribe(userId, request.getName(), request.getType());
        }
        return batchSubscribe(userId, request.getType());
    }

    /**
     * 通过用户ID获取订阅消息
     *
     * @param userId 用户ID
     * @return 订阅消息
     */
    private List<SysUserSubscribeMsg> list(String userId) {
//        LambdaQueryWrapper<SysUserSubscribeMsg> eq = Wrappers.lambdaQuery(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId);
//        return sysUserSubscribeMsgService.list(eq);
        return sysUserSubscribeMsgService.listByUserId(userId);
    }


    /**
     * 批量订阅
     *
     * @param userId 用户ID
     * @param type   类型
     * @return 是否成功
     */
    private Boolean batchCancel(String userId, UserSubscribeMsgUpdateRequest.MsgType type) {
//        LambdaUpdateWrapper<SysUserSubscribeMsg> eq = Wrappers.lambdaUpdate(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId);
//        return cancel(eq, type);
        return sysUserSubscribeMsgService.cancelSubscribe(userId, null, type);
    }

    /**
     * 批量取消
     *
     * @param userId 用户ID
     * @param name   模块名称
     * @param type   类型
     * @return 是否成功
     */
    private Boolean batchCancel(String userId, String name, UserSubscribeMsgUpdateRequest.MsgType type) {
//        LambdaQueryWrapper<SysMessageSubscribe> eq = Wrappers.lambdaQuery(SysMessageSubscribe.class)
//                .eq(SysMessageSubscribe::getModule, name);
        SysMessageSubscribeQueryRequest eq = new SysMessageSubscribeQueryRequest();
        eq.setModule(name);
        List<SysMessageSubscribe> list = sysMessageSubscribeService.listEntity(eq);
        if (CollectionUtil.isEmpty(list)) {
            return Boolean.FALSE;
        }
        List<String> ids = list.stream().map(SysMessageSubscribe::getId).toList();
//        LambdaUpdateWrapper<SysUserSubscribeMsg> ud = Wrappers.lambdaUpdate(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId)
//                .in(SysUserSubscribeMsg::getSubscribeId, ids);
//        return cancel(ud, type);
        return sysUserSubscribeMsgService.batchCancelSubscribe(userId, ids, type);
    }

    private Boolean cancel(String userId, String id, UserSubscribeMsgUpdateRequest.MsgType type) {
//        LambdaUpdateWrapper<SysUserSubscribeMsg> eq = Wrappers.lambdaUpdate(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId)
//                .eq(SysUserSubscribeMsg::getSubscribeId, id);
//        return cancel(eq, type);
        return sysUserSubscribeMsgService.cancelSubscribe(userId, id, type);
    }

//    private boolean cancel(LambdaUpdateWrapper<SysUserSubscribeMsg> ud, UserSubscribeMsgUpdateRequest.MsgType type) {
//        switch (type) {
//            case EMAIL:
//                ud.set(SysUserSubscribeMsg::getIsEmail, false);
//                break;
//            case SMS:
//                ud.set(SysUserSubscribeMsg::getIsSms, false);
//                break;
//            case SITE:
//                ud.set(SysUserSubscribeMsg::getIsSite, false);
//                break;
//        }
//        return sysUserSubscribeMsgService.update(ud);
//    }

    /**
     * 批量订阅
     *
     * @param userId 用户ID
     * @param type   类型
     * @return 是否成功
     */
    private Boolean batchSubscribe(String userId, UserSubscribeMsgUpdateRequest.MsgType type) {
//        LambdaQueryWrapper<SysMessageSubscribe> eq = Wrappers.lambdaQuery(SysMessageSubscribe.class);
        List<SysMessageSubscribe> list = sysMessageSubscribeService.listEntity();
        if (CollectionUtil.isEmpty(list)) {
            return Boolean.FALSE;
        }
        List<String> ids = list.stream().map(SysMessageSubscribe::getId).toList();
//        LambdaUpdateWrapper<SysUserSubscribeMsg> ud = Wrappers.lambdaUpdate(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId)
//                .in(SysUserSubscribeMsg::getSubscribeId, ids);
//        return subscribe(ud, type);
        return sysUserSubscribeMsgService.batchSubscribe(userId, ids, type);
    }

    /**
     * 批量订阅
     *
     * @param userId 用户ID
     * @param name   模块名称
     * @param type   类型
     * @return 是否成功
     */
    private Boolean batchSubscribe(String userId, String name, UserSubscribeMsgUpdateRequest.MsgType type) {
//        LambdaQueryWrapper<SysMessageSubscribe> eq = Wrappers.lambdaQuery(SysMessageSubscribe.class)
//                .eq(SysMessageSubscribe::getModule, name);
        SysMessageSubscribeQueryRequest eq = new SysMessageSubscribeQueryRequest();
        eq.setModule(name);
        List<SysMessageSubscribe> list = sysMessageSubscribeService.listEntity(eq);
        if (CollectionUtil.isEmpty(list)) {
            return Boolean.FALSE;
        }
        List<String> ids = list.stream().map(SysMessageSubscribe::getId).toList();
//        LambdaUpdateWrapper<SysUserSubscribeMsg> ud = Wrappers.lambdaUpdate(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId)
//                .in(SysUserSubscribeMsg::getSubscribeId, ids);
//        return subscribe(ud, type);
        return sysUserSubscribeMsgService.batchSubscribe(userId, ids, type);
    }

    private Boolean subscribe(String userId, String id, UserSubscribeMsgUpdateRequest.MsgType type) {
//        LambdaUpdateWrapper<SysUserSubscribeMsg> eq = Wrappers.lambdaUpdate(SysUserSubscribeMsg.class)
//                .eq(SysUserSubscribeMsg::getUserId, userId)
//                .eq(SysUserSubscribeMsg::getSubscribeId, id);
//        return subscribe(eq, type);
        return sysUserSubscribeMsgService.subscribe(userId, id, type);
    }

//    private Boolean subscribe(LambdaUpdateWrapper<SysUserSubscribeMsg> ud, UserSubscribeMsgUpdateRequest.MsgType type) {
//        switch (type) {
//            case EMAIL:
//                ud.set(SysUserSubscribeMsg::getIsEmail, true);
//                break;
//            case SMS:
//                ud.set(SysUserSubscribeMsg::getIsSms, true);
//                break;
//            case SITE:
//                ud.set(SysUserSubscribeMsg::getIsSite, true);
//                break;
//        }
//        return sysUserSubscribeMsgService.update(ud);
//
//    }

    /**
     * 同步订阅消息
     *
     * @param userId 用户ID
     */
    private void sync(String userId) {
        List<SysMessageSubscribe> list = sysMessageSubscribeService.listEntity();
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        Map<String, SysMessageSubscribe> subscribeMap = list.stream().collect(Collectors.toMap(SysMessageSubscribe::getId, Function.identity()));
        Set<String> subscribeIds = subscribeMap.keySet();
//        List<SysUserSubscribeMsg> subscribed = sysUserSubscribeMsgService.list(Wrappers.lambdaQuery(SysUserSubscribeMsg.class).eq(SysUserSubscribeMsg::getUserId, userId));
        List<SysUserSubscribeMsg> subscribed = sysUserSubscribeMsgService.listByUserId(userId);
        List<String> subscribedIds = subscribed.stream().map(SysUserSubscribeMsg::getSubscribeId).toList();

        // 还未订阅的
        List<String> notSubscribed = subscribeIds.stream().filter(s -> !subscribedIds.contains(s)).toList();
        if (CollectionUtil.isEmpty(notSubscribed)) {
            return;
        }
        // 删除已经取消订阅的
        List<String> cancelSubscribed = subscribedIds.stream().filter(s -> !subscribeIds.contains(s)).toList();
        if (CollectionUtil.isNotEmpty(cancelSubscribed)) {
//            sysUserSubscribeMsgService.remove(Wrappers.lambdaQuery(SysUserSubscribeMsg.class)
//                    .eq(SysUserSubscribeMsg::getUserId, userId)
//                    .in(SysUserSubscribeMsg::getSubscribeId, cancelSubscribed));
            sysUserSubscribeMsgService.deleteByUserIdAndSubscribeIds(userId, cancelSubscribed);
        }

        List<SysUserSubscribeMsg> save = notSubscribed.stream().map(s -> {
            SysUserSubscribeMsg sysUserSubscribeMsg = new SysUserSubscribeMsg();
            sysUserSubscribeMsg.setUserId(userId);
            sysUserSubscribeMsg.setSubscribeId(s);
            sysUserSubscribeMsg.setIsEmail(false);
            sysUserSubscribeMsg.setIsSms(false);
            sysUserSubscribeMsg.setIsSite(false);
            return sysUserSubscribeMsg;
        }).toList();
        sysUserSubscribeMsgService.saveBatch(save);
    }

}
