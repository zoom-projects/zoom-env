package com.hb0730.zoom.sys.biz.system.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.open.api.SysOpenApiQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiGroupVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOpenApiVO;
import com.hb0730.zoom.sys.biz.system.service.SysOpenApiService;
import com.hb0730.zoom.sys.define.operator.OpenApiOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@RestController
@RequestMapping("/sys/open/api")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "基础设施: 开放接口")
public class SysOpenApiController {
    private final SysOpenApiService sysOpenApiService;


    /**
     * 是否存在code
     *
     * @param code code
     * @param id   id
     * @return .
     */
    @GetMapping("/has_code")
    @Operation(summary = "标识符是否存在")
    public R<String> hasCode(String code, @RequestParam(required = false) String id) {
        return sysOpenApiService.hasCode(code, id);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询开放接口")
    @PreAuthorize("hasAuthority('sys:open:api:query')")
    public R<Page<SysOpenApiVO>> page(SysOpenApiQueryRequest request) {
        return R.OK(sysOpenApiService.page(request));
    }

    @GetMapping("/list")
    @Operation(summary = "查询开放接口")
    public R<List<SysOpenApiVO>> list(SysOpenApiQueryRequest request) {
        return R.OK(sysOpenApiService.list(request));
    }

    /**
     * 查询开放接口组
     *
     * @return .
     */
    @GetMapping("/group")
    @Operation(summary = "查询开放接口组")
    public R<List<SysOpenApiGroupVO>> group(SysOpenApiQueryRequest request) {
        return R.OK(sysOpenApiService.group(request));
    }


    /**
     * 保存开放接口
     *
     * @param request .
     * @return .
     */
    @PostMapping("/save")
    @Operation(summary = "保存开放接口")
    @OperatorLog(value = OpenApiOperatorType.ADD)
    @PreAuthorize("hasAuthority('sys:open:api:save')")
    public R<String> save(@RequestBody SysOpenApiCreateRequest request) {
        sysOpenApiService.create(request);
        return R.OK();
    }

    /**
     * 更新开放接口
     *
     * @param id      id
     * @param request .
     * @return .
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "更新开放接口")
    @OperatorLog(value = OpenApiOperatorType.UPDATE)
    @PreAuthorize("hasAuthority('sys:open:api:update')")
    public R<String> update(@PathVariable String id, @RequestBody SysOpenApiCreateRequest request) {
        sysOpenApiService.updateById(id, request);
        return R.OK();
    }

    @Operation(summary = "删除开放接口")
    @DeleteMapping("/del")
    @OperatorLog(value = OpenApiOperatorType.DELETE)
    @PreAuthorize("hasAuthority('sys:open:api:del')")
    public R<String> delete(String id) {
        sysOpenApiService.deleteById(id);
        return R.OK();
    }
}
