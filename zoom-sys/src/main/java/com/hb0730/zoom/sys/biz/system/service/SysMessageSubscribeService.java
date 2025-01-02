package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.message.entity.SysMessageSubscribe;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysMessageSubscribeConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysMessageSubscribeMapper;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeGroupVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@org.springframework.stereotype.Service
@Slf4j
public class SysMessageSubscribeService extends SuperServiceImpl<String, SysMessageSubscribeQueryRequest,
        SysMessageSubscribeVO, SysMessageSubscribe, SysMessageSubscribeCreateRequest,
        SysMessageSubscribeUpdateRequest, SysMessageSubscribeMapper, SysMessageSubscribeConvert> {

    /**
     * 是否存在编码
     *
     * @param code 编码
     * @param id   需要排除的id
     * @return 是否存在
     */
    public boolean hasCode(String code, String id) {
        LambdaQueryWrapper<SysMessageSubscribe> eq = Wrappers.lambdaQuery(SysMessageSubscribe.class)
                .eq(SysMessageSubscribe::getCode, code);
        if (StrUtil.isNotBlank(id)) {
            eq.ne(SysMessageSubscribe::getId, id);
        }
        return this.exists(eq);
    }


    /**
     * 分组
     *
     * @param request request
     * @return .
     */
    public List<SysMessageSubscribeGroupVO> group(SysMessageSubscribeQueryRequest request) {
        List<SysMessageSubscribeVO> list = list(request);
        return list.stream().collect(Collectors.groupingBy(SysMessageSubscribeVO::getModule))
                .entrySet().stream().map(entry -> {
                    SysMessageSubscribeGroupVO groupVO = new SysMessageSubscribeGroupVO();
                    groupVO.setModule(entry.getKey());
                    groupVO.setChildren(mapstruct.toGroup(entry.getValue()));
                    return groupVO;
                }).collect(Collectors.toList());
    }

    @Override
    public boolean create(SysMessageSubscribeCreateRequest request) {
        String code = request.getCode();
        if (this.exists(Wrappers.lambdaQuery(SysMessageSubscribe.class)
                .eq(SysMessageSubscribe::getCode, code))) {
            log.error("消息订阅编码已存在:{}", code);
            throw new RuntimeException("消息订阅编码已存在:" + code);
        }
        return super.create(request);
    }

    @Override
    public boolean updateById(String s, SysMessageSubscribeUpdateRequest request) {
        String code = request.getCode();
        if (this.exists(Wrappers.lambdaQuery(SysMessageSubscribe.class)
                .eq(SysMessageSubscribe::getCode, code)
                .ne(SysMessageSubscribe::getId, s))) {
            log.error("消息订阅编码已存在:{}", code);
            throw new RuntimeException("消息订阅编码已存在:" + code);
        }
        return super.updateById(s, request);
    }
}