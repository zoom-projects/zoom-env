package com.hb0730.zoom.sys.biz.system.model.request.message;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.annotation.Like;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 消息订阅查询
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "消息订阅查询")
public class SysMessageSubscribeQueryRequest extends PageRequest {
    @Schema(description = "归属模块")
    @Like
    private String module;
    @Schema(description = "标识")
    @Equals
    private String code;
    @Schema(description = "名称")
    @Like
    private String name;
}
