package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.core.service.BaseService;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysNoticeMessageVO;
import com.hb0730.zoom.sys.biz.system.repository.SysNoticeMessageRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@org.springframework.stereotype.Service
@Slf4j
public class SysNoticeMessageService extends BaseService<String, SysNoticeMessageQueryRequest,
        SysNoticeMessageVO, SysNoticeMessage, SysNoticeMessageCreateRequest, SysNoticeMessageCreateRequest,
        SysNoticeMessageRepository> {

    /**
     * 统计数量
     *
     * @param userId 用户id
     * @param status 状态
     * @return 数量
     */
    public long count(String userId, Integer status) {
        return repository.count(userId, status);
    }

    /**
     * 更新状态
     *
     * @param status   状态
     * @param eqId     消息id
     * @param eqUserId 用户id
     * @param eqStatus 状态
     * @return 是否成功
     */
    public boolean updateStatusBy(Integer status, String eqId, String eqUserId, Integer eqStatus) {
        return repository.updateStatusBy(status, eqId, eqUserId, eqStatus);
    }

    /**
     * 删除消息
     *
     * @param id     消息id
     * @param userId 用户id
     * @param status 状态
     * @return 是否成功
     */
    public boolean deleteBy(String id, String userId, Integer status) {
        return repository.deleteBy(id, userId, status);
    }
}
