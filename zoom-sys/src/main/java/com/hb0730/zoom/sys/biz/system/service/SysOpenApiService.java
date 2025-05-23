package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.core.service.BaseService;
import com.hb0730.zoom.base.sys.system.entity.SysOpenApi;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiGroupVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiVO;
import com.hb0730.zoom.sys.biz.system.repository.SysOpenApiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class SysOpenApiService extends BaseService<String, SysOpenApiQueryRequest, SysOpenApiVO,
        SysOpenApi, SysOpenApiCreateRequest, SysOpenApiCreateRequest, SysOpenApiRepository> {

    public R<String> hasCode(String code, String id) {
//        LambdaQueryWrapper<SysOpenApi> queryWrapper = Wrappers.lambdaQuery(SysOpenApi.class)
//                .eq(SysOpenApi::getApiCode, code);
//        if (StrUtil.isNotBlank(id)) {
//            queryWrapper.ne(SysOpenApi::getId, id);
//        }
//        boolean present = baseMapper.of(queryWrapper).present();
        boolean present = repository.codeExists(code, id);
        return present ? R.NG("标识符已存在") : R.OK("标识符不存在");
    }


    /**
     * 分组
     *
     * @param request request
     * @return .
     */
    public List<SysOpenApiGroupVO> group(SysOpenApiQueryRequest request) {
        List<SysOpenApiVO> list = list(request);
        return list.stream().collect(Collectors.groupingBy(SysOpenApiVO::getModule))
                .entrySet().stream().map(entry -> {
                    SysOpenApiGroupVO groupVO = new SysOpenApiGroupVO();
                    groupVO.setModule(entry.getKey());
                    groupVO.setChildren(repository.getMapstruct().toGroup(entry.getValue()));
                    return groupVO;
                }).collect(Collectors.toList());
    }

}
