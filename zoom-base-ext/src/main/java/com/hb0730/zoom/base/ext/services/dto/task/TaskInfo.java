package com.hb0730.zoom.base.ext.services.dto.task;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/26
 */
@Data
public class TaskInfo implements Serializable {
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 任务ID
     */
    private String id;
    /**
     * 任务编号
     */
    private String taskNum;
    /**
     * 任务执行批次号
     */
    private String lotNo;

    /**
     * 任务类型
     */
    private String type;

    /**
     * 服务器文件名称 =>objectKey
     */
    private String fileName;
    /**
     * 服务器文件路径 =>filePath
     */
    private String filePath;
    /**
     * 服务器文件地址 =>accessUrl
     */
    private String fileUrl;
    /**
     * 需要执行任务参数：任务内容
     */
    private String disContent;

    /**
     * 任务状态
     */
    private Integer disState;

    /**
     * 任务开始时间
     */
    private String disTimeBegin;
    /**
     * 任务结束时间
     */
    private String disTimeEnd;

    /**
     * 处理件数
     */
    private String disCount;
    /**
     * 失败件数
     */
    private Integer failCount;
    /**
     * 处理备注
     */
    private String message;
}
