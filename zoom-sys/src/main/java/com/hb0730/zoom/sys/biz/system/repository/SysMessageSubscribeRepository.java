package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.message.entity.SysMessageSubscribe;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysMessageSubscribeConvert;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeVO;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysMessageSubscribeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysMessageSubscribeRepository extends BaseRepository<String, SysMessageSubscribeQueryRequest,
        SysMessageSubscribeVO, SysMessageSubscribe, SysMessageSubscribeCreateRequest,
        SysMessageSubscribeUpdateRequest, SysMessageSubscribeMapper, SysMessageSubscribeConvert> {

    /**
     * 是否存在编码
     *
     * @param code 编码
     * @param id   需要排除的id
     * @return 是否存在
     */
    public boolean isCodeExists(String code, String id) {
        LambdaQueryWrapper<SysMessageSubscribe> eq = Wrappers.lambdaQuery(SysMessageSubscribe.class)
                .eq(SysMessageSubscribe::getCode, code);
        if (StrUtil.isNotBlank(id)) {
            eq.ne(SysMessageSubscribe::getId, id);
        }
        return this.exists(eq);
    }

}
