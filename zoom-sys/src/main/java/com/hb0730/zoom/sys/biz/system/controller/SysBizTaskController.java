package com.hb0730.zoom.sys.biz.system.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.ext.security.SecurityUtils;
import com.hb0730.zoom.sys.biz.system.model.request.task.SysBizTaskQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysBizTaskVO;
import com.hb0730.zoom.sys.biz.system.service.SysBizTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@RestController
@RequestMapping("/sys/task")
@Tag(name = "基础设施：任务管理")
@Slf4j
@RequiredArgsConstructor
public class SysBizTaskController {
    private final SysBizTaskService sysBizTaskService;


    /**
     * 分页查询
     *
     * @param request 请求参数
     * @return 分页数据
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询任务", description = "根据页面指定的查询条件分页查询任务")
    public R<Page<SysBizTaskVO>> page(SysBizTaskQueryRequest request) {
        SecurityUtils.getLoginUsername().ifPresent(request::setCreatedBy);
        Page<SysBizTaskVO> page = sysBizTaskService.page(request);
        return R.OK(page);
    }

}
