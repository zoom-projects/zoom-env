package com.hb0730.zoom.sys.biz.system.model.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 用户访问令牌创建请求
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户访问令牌创建请求")
public class SysUserAccessTokenCreateRequest implements Domain {
    @Schema(description = "名称")
    private String name;
    @Schema(description = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    @Schema(description = "描述")
    private String description;
    /**
     * 开放接口ID
     */
    @Schema(description = "开放接口ID")
    private List<String> openApiIds;
}
