package com.hb0730.zoom.sys.biz.system.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.utils.TreeUtil;
import com.hb0730.zoom.sys.biz.system.model.dto.SysPermissionDTO;
import com.hb0730.zoom.sys.biz.system.model.request.SysPermissionQuery;
import com.hb0730.zoom.sys.biz.system.model.vo.SysPermissionTreeVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysPermissionVO;
import com.hb0730.zoom.sys.biz.system.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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

/**
 * 菜单权限
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
@RestController
@RequestMapping("/sys/permission")
@Slf4j
@Tag(name = "系统管理: 管理")
@Validated
@RequiredArgsConstructor
public class SysPermissionController {
    private final SysPermissionService permissionService;

    /**
     * 菜单树
     *
     * @return 菜单树
     */
    @GetMapping("/tree")
    @Schema(description = "菜单树")
    public R<List<SysPermissionTreeVO>> tree() {
        List<SysPermissionTreeVO> nodes = permissionService.tree();
        List<SysPermissionTreeVO> tree = TreeUtil.build(nodes);
        return R.OK(tree);
    }

    /**
     * 权限列表
     *
     * @param query 查询条件
     * @return 权限列表
     */
    @GetMapping("/list")
    @Operation(summary = "权限列表")
    public R<List<SysPermissionVO>> list(SysPermissionQuery query) {
        List<SysPermissionDTO> permissionDtoList = permissionService.list(query);
        List<SysPermissionVO> res = permissionService.getMapstruct().toVo(permissionDtoList);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存权限")
    public R<String> save(@RequestBody SysPermissionDTO dto) {
        permissionService.dtoSave(dto);
        return R.OK();
    }

    @Operation(summary = "更新权限")
    @PutMapping("/update/{id}")
    public R<String> update(@PathVariable String id, @RequestBody SysPermissionDTO dto) {
        permissionService.dtoUpdate(dto.getId(), dto);
        return R.OK();
    }

    /**
     * 删除权限
     *
     * @param id 权限id
     * @return 是否成功
     */
    @Operation(summary = "删除权限")
    @DeleteMapping("/del")
    public R<String> deletePermission(String id) {
        permissionService.deletePermission(id);
        return R.OK();
    }

}
