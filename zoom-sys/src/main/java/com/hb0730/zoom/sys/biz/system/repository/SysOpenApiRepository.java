package com.hb0730.zoom.sys.biz.system.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.system.entity.SysOpenApi;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysOpenApiConvert;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiVO;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysOpenApiMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysOpenApiRepository extends BaseRepository<String, SysOpenApiQueryRequest, SysOpenApiVO,
        SysOpenApi, SysOpenApiCreateRequest, SysOpenApiCreateRequest, SysOpenApiMapper, SysOpenApiConvert> {

    /**
     * 根据code和id查询
     *
     * @param code code
     * @param id   需要排除的ID
     * @return 是否存在
     */
    public boolean codeExists(String code, String id) {
        LambdaQueryWrapper<SysOpenApi> eq = Wrappers.lambdaQuery(SysOpenApi.class)
                .eq(SysOpenApi::getApiCode, code);
        if (StrUtil.isNotBlank(id)) {
            eq.ne(SysOpenApi::getId, id);
        }
        return baseMapper.of(eq).present();
    }
}
