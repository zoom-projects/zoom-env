package com.hb0730.zoom.sys.biz.system.model.request.message;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.annotation.GreaterThanEqual;
import com.hb0730.zoom.mybatis.query.annotation.LessThanEqual;
import com.hb0730.zoom.mybatis.query.annotation.Like;
import com.hb0730.zoom.mybatis.query.annotation.OrLike;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "通知消息查询")
public class SysNoticeMessageQueryRequest extends PageRequest {
    /**
     * 关键字
     */
    @Schema(description = "关键字")
    @Like(value = "title")
    @OrLike(value = "content")
    private String keyword;
    @Equals
    @Schema(description = "接收人id")
    private String receiverId;
    @Equals
    @Schema(description = "接收人")
    private String receiver;
    @Equals
    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "开始时间")
    @GreaterThanEqual
    private String beginTime;

    @Schema(description = "结束时间")
    @LessThanEqual
    private String endTime;
}
