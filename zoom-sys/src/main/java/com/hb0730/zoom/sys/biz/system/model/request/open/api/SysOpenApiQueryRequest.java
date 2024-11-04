package com.hb0730.zoom.sys.biz.system.model.request.open.api;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.annotation.Like;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "开放接口查询请求")
public class SysOpenApiQueryRequest extends PageRequest {

    /**
     * 接口标识
     */
    @Schema(description = "接口标识")
    @Equals
    private String apiCode;
    /**
     * 接口名称
     */
    @Schema(description = "接口名称")
    @Like
    private String apiName;
    /**
     * 所属模块
     */
    @Schema(description = "所属模块")
    @Like
    private String module;
}
