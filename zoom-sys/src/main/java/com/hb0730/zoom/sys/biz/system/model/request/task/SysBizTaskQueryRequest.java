package com.hb0730.zoom.sys.biz.system.model.request.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysBizTaskQueryRequest extends PageRequest {


    /**
     * 创建者
     */
    @Schema(description = "创建者", hidden = true)
    @JsonIgnore
    @Equals(allowNull = true)
    private String createdBy;
}
