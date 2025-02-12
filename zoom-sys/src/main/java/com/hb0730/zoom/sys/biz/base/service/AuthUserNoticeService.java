package com.hb0730.zoom.sys.biz.base.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.enums.NoticeMessageStatusEnums;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysNoticeMessageVO;
import com.hb0730.zoom.sys.biz.system.service.SysNoticeMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserNoticeService {
    private final SysNoticeMessageService sysNoticeMessageService;

    /**
     * 查询系统消息列表
     *
     * @param request 请求
     * @return 系统消息列表
     */
    public Page<SysNoticeMessageVO> page(SysNoticeMessageQueryRequest request) {
        return sysNoticeMessageService.page(request);
    }

    /**
     * 查询系统消息列表
     *
     * @param request 请求
     * @return 系统消息列表
     */
    public List<SysNoticeMessageVO> list(SysNoticeMessageQueryRequest request) {
        return sysNoticeMessageService.list(request);
    }

    /**
     * 统计数量
     *
     * @param userId      用户id
     * @param queryUnread 是否查询未读
     * @return 数量
     */
    public Long count(String userId, Boolean queryUnread) {
        LambdaQueryWrapper<SysNoticeMessage> eq = Wrappers.lambdaQuery(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getReceiverId, userId);
        if (queryUnread) {
            eq.eq(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.UN_READ.getCode());
        }
        return sysNoticeMessageService.count(eq);
    }

    /**
     * 是否有未读消息
     *
     * @param userId 用户id
     * @return 是否有未读消息
     */
    public Boolean checkHasUnreadMessage(String userId) {
        LambdaQueryWrapper<SysNoticeMessage> eq = Wrappers.lambdaQuery(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getReceiverId, userId)
                .eq(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.UN_READ.getCode());
        return sysNoticeMessageService.count(eq) > 0;
    }

    /**
     * 更新系统消息为已读
     *
     * @param id     消息id
     * @param userId 用户id
     * @return 是否成功
     */
    public Boolean read(String id, String userId) {
        LambdaUpdateWrapper<SysNoticeMessage> ud = Wrappers.lambdaUpdate(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getId, id)
                .eq(SysNoticeMessage::getReceiverId, userId)
                .eq(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.UN_READ.getCode())
                .set(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.READ.getCode());
        return sysNoticeMessageService.update(ud);
    }

    /**
     * 更新系统消息为已读
     *
     * @param userId 用户id
     * @return 是否成功
     */
    public Boolean readAll(String userId) {
        LambdaUpdateWrapper<SysNoticeMessage> ud = Wrappers.lambdaUpdate(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getReceiverId, userId)
                .eq(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.UN_READ.getCode())
                .set(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.READ.getCode());
        return sysNoticeMessageService.update(ud);
    }

    /**
     * 删除系统消息
     *
     * @param id     消息id
     * @param userId 用户id
     * @return 是否成功
     */
    public Boolean removeById(String id, String userId) {
        LambdaQueryWrapper<SysNoticeMessage> eq = Wrappers.lambdaQuery(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getId, id)
                .eq(SysNoticeMessage::getReceiverId, userId);
        return sysNoticeMessageService.remove(eq);
    }

    /**
     * 清空系统消息
     *
     * @param userId 用户id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean clearSystemMessage(String userId) {
        LambdaQueryWrapper<SysNoticeMessage> eq = Wrappers.lambdaQuery(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getReceiverId, userId)
                .eq(SysNoticeMessage::getStatus, NoticeMessageStatusEnums.READ.getCode());
        return sysNoticeMessageService.remove(eq);
    }
}
