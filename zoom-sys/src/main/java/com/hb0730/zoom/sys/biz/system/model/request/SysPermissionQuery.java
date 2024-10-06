package com.hb0730.zoom.sys.biz.system.model.request;

import com.hb0730.zoom.mybatis.query.annotation.Like;
import com.hb0730.zoom.mybatis.query.doamin.PageParams;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限查询
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "菜单&权限查询")
public class SysPermissionQuery extends PageParams {
    @Like
    @Schema(description = "名称")
    private String title;
    
    @Schema(description = "父级ID")
    private String parentId;
}
