package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.message.entity.SysNoticeMessage;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysNoticeMessageConvert;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysNoticeMessageVO;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysNoticeMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysNoticeMessageRepository extends BaseRepository<String, SysNoticeMessageQueryRequest,
        SysNoticeMessageVO, SysNoticeMessage, SysNoticeMessageCreateRequest, SysNoticeMessageCreateRequest,
        SysNoticeMessageMapper, SysNoticeMessageConvert> {

    /**
     * 统计数量
     *
     * @param userId 用户id
     * @param status 状态
     * @return 数量
     */
    public long count(String userId, Integer status) {
        LambdaQueryWrapper<SysNoticeMessage> eq = Wrappers.lambdaQuery(SysNoticeMessage.class)
                .eq(SysNoticeMessage::getReceiverId, userId);
        if (null != status) {
            eq.eq(SysNoticeMessage::getStatus, status);
        }
        return baseMapper.of(eq).count();
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
        if (StrUtil.isBlank(eqId) && StrUtil.isBlank(eqUserId)) {
            return false;
        }
        LambdaUpdateWrapper<SysNoticeMessage> up = Wrappers.lambdaUpdate(SysNoticeMessage.class);
        if (StrUtil.isNotBlank(eqId)) {
            up.eq(SysNoticeMessage::getId, eqId);
        }
        if (StrUtil.isNotBlank(eqUserId)) {
            up.eq(SysNoticeMessage::getReceiverId, eqUserId);
        }
        if (null != eqStatus) {
            up.eq(SysNoticeMessage::getStatus, eqStatus);
        }

        up.set(SysNoticeMessage::getStatus, status);

        return baseMapper.update(up) > 0;
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
        if (StrUtil.isBlank(id) && StrUtil.isBlank(userId)) {
            return false;
        }
        LambdaUpdateWrapper<SysNoticeMessage> up = Wrappers.lambdaUpdate(SysNoticeMessage.class);
        if (StrUtil.isNotBlank(id)) {
            up.eq(SysNoticeMessage::getId, id);
        }
        if (StrUtil.isNotBlank(userId)) {
            up.eq(SysNoticeMessage::getReceiverId, userId);
        }
        if (null != status) {
            up.eq(SysNoticeMessage::getStatus, status);
        }

        return baseMapper.delete(up) > 0;
    }
}
