package com.hb0730.zoom.sys.biz.message.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageQueryRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageVO;
import com.hb0730.zoom.sys.biz.message.service.SysMessageService;
import com.hb0730.zoom.sys.define.operator.SysMessageTemplateOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/16
 */
@RestController
@RequestMapping("/sys/message")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "基础设施: 消息管理")
@Validated
public class SysMessageController {
    private final SysMessageService service;

    @GetMapping("/page")
    @Operation(summary = "分页查询消息")
    public R<Page<SysMessageVO>> page(SysMessageQueryRequest request) {
        return R.OK(service.page(request));
    }

    /**
     * 查询消息
     *
     * @param request 请求
     * @return 消息
     */
    @GetMapping("/list")
    @Operation(summary = "查询消息")
    public R<List<SysMessageVO>> list(SysMessageQueryRequest request) {
        return R.OK(service.list(request));
    }

    @GetMapping("/decrypt")
    @Operation(summary = "解密")
    public R<String> decrypt(String content) {
        return service.decrypt(content);
    }


    /**
     * 保存消息
     *
     * @param request 请求
     * @return 是否成功
     */
    @PostMapping("/save")
    @Operation(summary = "保存消息")
    @OperatorLog(SysMessageTemplateOperatorType.ADD)
    public R<String> save(@RequestBody SysMessageCreateRequest request) {
        service.create(request);
        return R.OK();
    }


    /**
     * 删除消息
     *
     * @param id id
     * @return 是否成功
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除消息")
    @OperatorLog(SysMessageTemplateOperatorType.DELETE)
    public R<String> delete(String id) {
        service.removeById(id);
        return R.OK();
    }


}
