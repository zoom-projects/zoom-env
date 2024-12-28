package com.hb0730.zoom.sys.biz.system.model.request.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@Data
@ToString
public class SysBizTaskCreateRequest implements Domain {

    /**
     * 任务类型
     */
    @Schema(description = "任务类型")
    private String type;

    /**
     * 批次号
     */
    @Schema(description = "批次号")
    private String lotNo;

    /**
     * 业务类型
     */
    @Schema(description = "业务类型")
    private String appName;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径", hidden = true)
    @JsonIgnore
    private String filePath;
    /**
     * 文件名
     */
    @Schema(description = "文件名")
    private String fileName;

    /**
     * 文件url
     */
    @Schema(description = "文件url", hidden = true)
    @JsonIgnore
    private String fileUrl;

    /**
     * 页面查询参数
     */
    @Schema(description = "页面查询参数")
    private Map<String, String> param;
}

