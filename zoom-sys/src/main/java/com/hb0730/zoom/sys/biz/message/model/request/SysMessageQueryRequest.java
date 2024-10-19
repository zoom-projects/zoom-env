package com.hb0730.zoom.sys.biz.message.model.request;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.annotation.Like;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询消息")
public class SysMessageQueryRequest extends PageRequest {
    @Schema(description = "消息类型")
    @Equals
    private String msgType;

    @Schema(description = "消息标题")
    @Like
    private String msgTitle;

    /**
     * 消息接收人
     */
    @Schema(description = "消息接收人")
    @Equals
    private String msgReceiver;

}
