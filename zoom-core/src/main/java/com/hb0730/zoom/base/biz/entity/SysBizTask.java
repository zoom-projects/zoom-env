package com.hb0730.zoom.base.biz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hb0730.zoom.base.entity.BizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 任务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_biz_task")
public class SysBizTask extends BizEntity {
    /**
     * 任务分类
     *
     * @see com.hb0730.zoom.base.enums.TaskTypeEnums
     */
    @TableField(value = "category")
    private String category;

    /**
     * 任务类型
     * <li>E 导出</li>
     * <li>I 导入</li>
     */
    @TableField(value = "type")
    private String type;

    /**
     * 业务分类
     */
    @TableField(value = "business_type")
    private String businessType;

    /**
     * 任务号
     */
    @TableField(value = "task_num")
    private String taskNum;

    /**
     * 任务名
     */
    @TableField(value = "task_name")
    private String taskName;

    /**
     * 任务描述
     */
    @TableField(value = "dis_describe")
    private String disDescribe;

    /**
     * 任务执行批次号
     */
    @TableField(value = "lot_no")
    private String lotNo;

    /**
     * 需要执行任务参数：任务内容
     */
    @TableField(value = "dis_content")
    private String disContent;

    /**
     * 服务器文件名称=>objectKey
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 服务器文件路径=>filePath
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 下载地址，全路径 =>accessUrl
     */
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 处理状态
     */
    @TableField(value = "dis_state")
    private Integer disState;

    /**
     * 开始时间
     */
    @TableField(value = "dis_time_begin")
    private String disTimeBegin;

    /**
     * 结束时间
     */
    @TableField(value = "dis_time_end")
    private String disTimeEnd;

    /**
     * 处理件数
     */
    @TableField(value = "dis_count")
    private String disCount;

    /**
     * 失败件数
     */
    @TableField(value = "fail_count")
    private Integer failCount;

    /**
     * 执行结果信息
     */
    @TableField(value = "message")
    private String message;

//    /**
//     * 审核人
//     */
//    @TableField(value = "auditor")
//    private String auditor;
//
//    /**
//     * 审核时间
//     */
//    @TableField(value = "audit_time")
//    private String auditTime;
//
//    /**
//     * 审核备注
//     */
//    @TableField(value = "audit_memo")
//    private String auditMemo;
}
