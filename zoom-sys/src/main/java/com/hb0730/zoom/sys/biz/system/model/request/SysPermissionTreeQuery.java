package com.hb0730.zoom.sys.biz.system.model.request;

import com.hb0730.zoom.base.utils.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@Data
@Schema(description = "树形菜单权限查询条件")
public class SysPermissionTreeQuery implements Serializable {
    /**
     * 需要查询的菜单类型
     */
    @Schema(description = "需要查询的菜单类型")
    private String menuType = "0,1,2";

    /**
     * 获取菜单类型
     *
     * @return 菜单类型
     */
    public List<String> getMenuTypes() {
        if (StrUtil.isBlank(menuType)) {
            return Arrays.asList("0", "1", "2");
        }
        return Arrays.asList(menuType.split(","));
    }
}
