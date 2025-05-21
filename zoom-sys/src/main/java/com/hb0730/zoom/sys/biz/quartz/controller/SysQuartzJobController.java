package com.hb0730.zoom.sys.biz.quartz.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.operator.log.core.annotation.OperatorLog;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzCreateRequest;
import com.hb0730.zoom.sys.biz.quartz.model.request.SysQuartzJobQueryRequest;
import com.hb0730.zoom.sys.biz.quartz.model.vo.SysQuartzJobVO;
import com.hb0730.zoom.sys.biz.quartz.service.SysQuartzJobService;
import com.hb0730.zoom.sys.define.operator.SysQuartzJobOperatorType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@RestController
@RequestMapping("/sys/job")
@Tag(name = "基础-计划任务")
@RequiredArgsConstructor
@Slf4j
@Validated
public class SysQuartzJobController {
    private final SysQuartzJobService quartzJobService;

    @GetMapping("/page")
    @Operation(summary = "分页查询计划任务")
    public R<Page<SysQuartzJobVO>> page(SysQuartzJobQueryRequest request) {
        return R.OK(quartzJobService.page(request));
    }

    @GetMapping("/list")
    @Operation(summary = "查询计划任务")
    public R<List<SysQuartzJobVO>> list(SysQuartzJobQueryRequest request) {
        return R.OK(quartzJobService.list(request));
    }

    @PostMapping("/save")
    @Operation(summary = "保存计划任务")
    @OperatorLog(SysQuartzJobOperatorType.ADD)
    public R<?> save(@Validated @RequestBody SysQuartzCreateRequest request) {
        quartzJobService.create(request);
        return R.OK();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新计划任务")
    @OperatorLog(SysQuartzJobOperatorType.EDIT)
    public R<String> update(@PathVariable String id, @Validated @RequestBody SysQuartzCreateRequest request) {
        quartzJobService.updateById(id, request);
        return R.OK();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计划任务")
    @OperatorLog(SysQuartzJobOperatorType.DELETE)
    public R<String> delete(String id) {
        quartzJobService.deleteById(id);
        return R.OK();
    }

    @PutMapping("/operate/{type}")
    @Operation(summary = "操作计划任务")
    @OperatorLog(SysQuartzJobOperatorType.OPERATE)
    public R<String> operateJob(@PathVariable String type, @RequestBody Map<String, String> body) {
        String id = body.get("id");
        if (StrUtil.isBlank(id)) {
            return R.NG("id不能为空");
        }
        return switch (type) {
            case "start" -> startJob(id);
            case "pause" -> pauseJob(id);
            case "run" -> runJob(id);
            default -> R.NG("未知操作");
        };
    }

    /**
     * 暂停任务
     *
     * @param id 任务id
     * @return 是否成功
     */
    public R<String> pauseJob(String id) {
        quartzJobService.pauseJob(id);
        return R.OK();
    }

    /**
     * 启动任务
     *
     * @param id 任务id
     * @return 是否成功
     */
    public R<String> startJob(String id) {
        return quartzJobService.resumeJob(id);
    }

    /**
     * 运行任务
     *
     * @param id 任务id
     * @return 是否成功
     */
    public R<String> runJob(String id) {
        return quartzJobService.executeJob(id);
    }


}
