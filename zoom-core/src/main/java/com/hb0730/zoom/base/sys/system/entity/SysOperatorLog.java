package com.hb0730.zoom.base.sys.system.entity;

import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 操作日志
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysOperatorLog extends BaseEntity {
    /**
     * 操作人ID
     */
    private String operatorId;
    /**
     * 操作人
     */
    private String operator;
    /**
     * traceId
     */
    private String traceId;
    /**
     * 请求ip
     */
    private String address;
    /**
     * 地址
     */
    private String location;

    /**
     * userAgent
     */
    private String userAgent;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作内容
     */
    private String content;
    /**
     * 请求参数
     */
    private String extra;
    /**
     * 操作结果，0-成功，1-失败
     */
    private Integer result;
    /**
     * 错误信息
     */
    private String errorMessage;
    /**
     * 返回值
     */
    private String returnValue;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

}
