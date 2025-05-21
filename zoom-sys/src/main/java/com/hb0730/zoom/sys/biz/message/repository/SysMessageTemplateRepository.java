package com.hb0730.zoom.sys.biz.message.repository;

import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.message.entity.SysMessageTemplate;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.message.convert.SysMessageTemplateConvert;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateQueryRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageTemplateVO;
import com.hb0730.zoom.sys.biz.message.repository.mapper.SysMessageTemplateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysMessageTemplateRepository extends BaseRepository<String, SysMessageTemplateQueryRequest,
        SysMessageTemplateVO, SysMessageTemplate, SysMessageTemplateCreateRequest, SysMessageTemplateUpdateRequest,
        SysMessageTemplateMapper, SysMessageTemplateConvert> {

    /**
     * 校验模板CODE是否存在
     *
     * @param code 模板CODE
     * @param id   需要排除的ID
     * @return 是否存在
     */
    public boolean codeExists(String code, String id) {
        return lambdaQuery()
                .eq(SysMessageTemplate::getTemplateCode, code)
                .ne(StrUtil.isNotBlank(id), SysMessageTemplate::getId, id)
                .exists();
    }
}
