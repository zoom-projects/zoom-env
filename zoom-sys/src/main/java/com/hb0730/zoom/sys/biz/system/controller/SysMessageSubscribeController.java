package com.hb0730.zoom.sys.biz.system.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysMessageSubscribeUpdateRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeGroupVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysMessageSubscribeVO;
import com.hb0730.zoom.sys.biz.system.service.SysMessageSubscribeService;
import com.hb0730.zoom.sys.define.operator.SysMessageSubscribeOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
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
 * 消息订阅管理
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@RestController
@RequestMapping("/sys/message/subscribe")
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "基础设施: 消息订阅")
public class SysMessageSubscribeController {
    private final SysMessageSubscribeService sysMessageSubscribeService;

    /**
     * 是否存在编码
     *
     * @param code 编码
     * @param id   需要排除的id
     * @return 是否存在
     */
    @GetMapping("/has_code")
    @Operation(summary = "是否存在编码")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "code", description = "编码", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "需要排除的id", required = false)
    })
    public R<Boolean> hasCode(String code, @RequestParam(required = false) String id) {
        return R.OK(sysMessageSubscribeService.hasCode(code, id));
    }

    /**
     * 查询组
     *
     * @return 组
     */
    @GetMapping("/group")
    @Operation(summary = "查询组")
    public R<List<SysMessageSubscribeGroupVO>> group(SysMessageSubscribeQueryRequest request) {
        List<SysMessageSubscribeGroupVO> group = sysMessageSubscribeService.group(request);
        return R.OK(group);
    }

    /**
     * 分页查询
     *
     * @param request 请求
     * @return 分页数据
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询消息订阅")
    @PreAuthorize("hasAnyAuthority('sys:message:subscribe:page')")
    public R<Page<SysMessageSubscribeVO>> page(SysMessageSubscribeQueryRequest request) {
        Page<SysMessageSubscribeVO> res = sysMessageSubscribeService.page(request);
        return R.OK(res);
    }


    /**
     * 保存
     *
     * @param request 请求
     * @return 是否成功
     */
    @Operation(summary = "保存消息订阅")
    @PreAuthorize("hasAnyAuthority('sys:message:subscribe:save')")
    @PostMapping("/save")
    @OperatorLog(SysMessageSubscribeOperatorType.ADD)
    public R<String> save(@RequestBody SysMessageSubscribeCreateRequest request) {
        sysMessageSubscribeService.create(request);
        return R.OK();
    }

    /**
     * 更新
     *
     * @param id      id
     * @param request 请求
     * @return 是否成功
     */
    @Operation(summary = "更新消息订阅")
    @PreAuthorize("hasAnyAuthority('sys:message:subscribe:update')")
    @PutMapping("/update/{id}")
    @OperatorLog(SysMessageSubscribeOperatorType.UPDATE)
    public R<String> updateById(@PathVariable String id, @RequestBody SysMessageSubscribeUpdateRequest request) {
        sysMessageSubscribeService.updateById(id, request);
        return R.OK();
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Operation(summary = "删除消息订阅")
    @PreAuthorize("hasAnyAuthority('sys:message:subscribe:delete')")
    @DeleteMapping("/del")
    @OperatorLog(SysMessageSubscribeOperatorType.DELETE)
    public R<String> removeById(String id) {
        sysMessageSubscribeService.removeById(id);
        return R.OK();
    }

}
