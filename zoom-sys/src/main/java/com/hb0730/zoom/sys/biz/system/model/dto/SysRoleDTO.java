package com.hb0730.zoom.sys.biz.system.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@Data
public class SysRoleDTO implements Serializable {
    @Schema(description = "id")
    private String id;
    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    @NotBlank(message = "角色标识不能为空")
    private String roleCode;
    /**
     * 角色状态
     */
    @Schema(description = "角色状态,0:禁用,1:启用")
    private Integer status = 1;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}