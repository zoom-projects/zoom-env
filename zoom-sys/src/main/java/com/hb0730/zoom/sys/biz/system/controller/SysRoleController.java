package com.hb0730.zoom.sys.biz.system.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.sys.biz.system.model.dto.SysRoleDTO;
import com.hb0730.zoom.sys.biz.system.model.request.SysRoleQuery;
import com.hb0730.zoom.sys.biz.system.model.vo.SysRoleVO;
import com.hb0730.zoom.sys.biz.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色 controller
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@RestController
@RequestMapping("/sys/role")
@Slf4j
@RequiredArgsConstructor
@Validated
public class SysRoleController {
    private final SysRoleService sysRoleService;


    /**
     * 是否存在
     *
     * @param id id
     * @return 是否存在
     */
    @GetMapping("/has_code")
    @Operation(summary = "code是否存在", description = "code是否存在,id存在则排除id")
    @Parameters({
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "需要排除的id", required = false),
            @io.swagger.v3.oas.annotations.Parameter(name = "code", description = "角色标识")
    })
    public R<String> hasCode(
            @RequestParam(required = false) String id,
            @RequestParam String code) {
        return sysRoleService.hasCode(id, code);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public R<Page<SysRoleVO>> page(SysRoleQuery query) {
        return R.OK(sysRoleService.pageV(query));
    }

    @GetMapping("/list")
    @Operation(summary = "查询所有")
    public R<List<SysRoleVO>> list(SysRoleQuery query) {
        return R.OK(sysRoleService.listV(query));
    }

    @Operation(summary = "保存")
    @PostMapping("/save")
    public R<String> save(@RequestBody SysRoleDTO dto) {
        sysRoleService.saveD(dto);
        return R.OK();
    }

    @Operation(summary = "更新")
    @PutMapping("/update/{id}")
    public R<String> update(@PathVariable String id, @RequestBody SysRoleDTO dto) {
        sysRoleService.updateD(id, dto);
        return R.OK();
    }

    /**
     * 获取权限
     *
     * @param id 角色id
     * @return 权限
     */
    @GetMapping("/perms")
    @Operation(summary = "获取权限")
    public R<List<String>> getPerms(String id) {
        List<String> perms = sysRoleService.getPerms(id);
        return R.OK(perms);
    }

    /**
     * 赋权
     *
     * @param id            角色id
     * @param permissionIds 权限id
     * @return 是否成功
     */
    @Operation(summary = "赋权")
    @PutMapping("/perms/{id}")
    public R<String> grant(@PathVariable String id, @RequestBody List<String> permissionIds) {
        sysRoleService.grant(id, permissionIds);
        return R.OK();
    }

    @Operation(summary = "删除")
    @DeleteMapping("/del")
    public R<String> del(String id) {
        sysRoleService.deleteById(id);
        return R.OK();
    }


}
