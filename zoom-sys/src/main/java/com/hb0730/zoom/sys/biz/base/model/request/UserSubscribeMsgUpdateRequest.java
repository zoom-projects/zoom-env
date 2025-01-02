package com.hb0730.zoom.sys.biz.base.model.request;

import com.hb0730.zoom.base.data.Domain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@Schema(description = "用户订阅消息更新")
public class UserSubscribeMsgUpdateRequest implements Domain {
    @Schema(description = "id")
    private String id;
    /**
     * 是否根节点
     */
    @Schema(description = "是否根节点")
    private Boolean isRoot;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
    /**
     * 值
     */
    @Schema(description = "值")
    @NotNull(message = "值不能为空")
    private Boolean value;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空")
    @Schema(description = "类型", allowableValues = {"SITE", "EMAIL", "SMS"})
    private MsgType type;


    public enum MsgType {
        SITE,
        EMAIL,
        SMS
    }


}
