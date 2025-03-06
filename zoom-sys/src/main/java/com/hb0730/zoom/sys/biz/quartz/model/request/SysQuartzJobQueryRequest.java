package com.hb0730.zoom.sys.biz.quartz.model.request;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询定时任务请求")
public class SysQuartzJobQueryRequest extends PageRequest {
    @Schema(description = "归属应用")
    @Equals
    private String appName;
    @Schema(description = "任务名称")
    @Equals
    private String jobClassName;
}
