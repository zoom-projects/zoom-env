package com.hb0730.zoom.sys.biz.base.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/11
 */
@Data
@EqualsAndHashCode
@Schema(description = "用户设置")
public class UserSettingsVO implements java.io.Serializable {

    /**
     * 是否开启消息通知
     */
    @Schema(description = "是否开启消息通知")
    private Boolean messageNotification;
}
