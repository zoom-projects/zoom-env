package com.hb0730.zoom.sys.biz.message.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.message.entity.SysMessageTemplate;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.message.convert.SysMessageTemplateConvert;
import com.hb0730.zoom.sys.biz.message.mapper.SysMessageTemplateMapper;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateQueryRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageTemplateVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@Service
@Slf4j
public class SysMessageTemplateService extends SuperServiceImpl<String, SysMessageTemplateQueryRequest,
        SysMessageTemplateVO, SysMessageTemplate, SysMessageTemplateCreateRequest, SysMessageTemplateUpdateRequest,
        SysMessageTemplateMapper, SysMessageTemplateConvert> {

    /**
     * 校验模板CODE是否存在
     *
     * @param code 模板CODE
     * @return 是否存在
     */
    public R<String> hasCode(String code, String id) {
        LambdaQueryWrapper<SysMessageTemplate> queryWrapper = Wrappers.lambdaQuery(SysMessageTemplate.class)
                .eq(SysMessageTemplate::getTemplateCode, code);
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.ne(SysMessageTemplate::getId, id);
        }
        boolean present = baseMapper.of(queryWrapper).present();
        if (present) {
            return R.NG("模板CODE已存在");
        } else {
            return R.OK();
        }
    }
}
