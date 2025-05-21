package com.hb0730.zoom.sys.biz.message.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateQueryRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageTemplateUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageTemplateVO;
import com.hb0730.zoom.sys.biz.message.service.SysMessageTemplateService;
import com.hb0730.zoom.sys.define.operator.SysMessageTemplateOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
 * @date 2024/10/16
 */
@RestController
@RequestMapping("/sys/message/template")
@Tag(name = "基础设施: 消息模板")
@Validated
@RequiredArgsConstructor
@Slf4j
public class SysMessageTemplateController {

    private final SysMessageTemplateService service;


    @Operation(summary = "校验模板CODE是否存在")
    @GetMapping("/has_code")
    public R<String> hasCode(String code, @RequestParam(required = false) String id) {
        return service.hasCode(code, id);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询消息模板")
    @PreAuthorize("hasAuthority('sys:message:template:query')")
    public R<Page<SysMessageTemplateVO>> page(SysMessageTemplateQueryRequest request) {
        return R.OK(service.page(request));
    }

    @GetMapping("/list")
    @Operation(summary = "查询消息模板")
    public R<List<SysMessageTemplateVO>> list(SysMessageTemplateQueryRequest request) {
        return R.OK(service.list(request));
    }

    @PostMapping("/save")
    @Operation(summary = "保存消息模板")
    @OperatorLog(SysMessageTemplateOperatorType.ADD)
    @PreAuthorize("hasAuthority('sys:message:template:add')")
    public R<String> save(@Validated @RequestBody SysMessageTemplateCreateRequest request) {
        service.create(request);
        return R.OK();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新消息模板")
    @OperatorLog(SysMessageTemplateOperatorType.EDIT)
    @PreAuthorize("hasAuthority('sys:message:template:update')")
    public R<String> update(@PathVariable String id, @Validated @RequestBody SysMessageTemplateUpdateRequest request) {
        service.updateById(id, request);
        return R.OK();
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除消息模板")
    @OperatorLog(SysMessageTemplateOperatorType.DELETE)
    @PreAuthorize("hasAuthority('sys:message:template:delete')")
    public R<String> delete(String id) {
        service.deleteById(id);
        return R.OK();
    }


}
