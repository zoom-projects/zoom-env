package com.hb0730.zoom.sys.biz.system.model.vo;

import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@Data
@Schema(description = "任务")
public class SysBizTaskVO implements Domain {
    @Schema(description = "任务ID")
    private String id;
    /**
     * 任务分类
     *
     * @see com.hb0730.zoom.base.enums.TaskTypeEnums
     */
    @Schema(description = "任务分类")
    private String category;

    /**
     * 任务类型
     * <li>E 导出</li>
     * <li>I 导入</li>
     */
    @Schema(description = "任务类型")
    private String type;

    /**
     * 业务分类
     */
    @Schema(description = "业务分类")
    private String businessType;

    /**
     * 任务号
     */
    @Schema(description = "任务号")
    private String taskNum;

    /**
     * 任务名
     */
    @Schema(description = "任务名")
    private String taskName;

    /**
     * 任务描述
     */
    @Schema(description = "任务描述")
    private String disDescribe;

    /**
     * 任务执行批次号
     */
    @Schema(description = "任务执行批次号")
    private String lotNo;

    /**
     * 需要执行任务参数：任务内容
     */
    @Schema(description = "需要执行任务参数：任务内容")
    private String disContent;

    /**
     * 服务器文件名称
     */
    @Schema(description = "服务器文件名称")
    private String fileName;

    /**
     * 处理状态
     */
    @Schema(description = "处理状态")
    private Integer disState;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String disTimeBegin;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String disTimeEnd;

    /**
     * 处理件数
     */
    @Schema(description = "处理件数")
    private String disCount;

    /**
     * 失败件数
     */
    @Schema(description = "失败件数")
    private Integer failCount;

    /**
     * 执行结果信息
     */
    @Schema(description = "执行结果信息")
    private String message;
}
