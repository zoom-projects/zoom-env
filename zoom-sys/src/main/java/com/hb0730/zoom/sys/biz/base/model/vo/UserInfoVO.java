package com.hb0730.zoom.sys.biz.base.model.vo;

import com.hb0730.zoom.base.enums.MaskEnums;
import com.hb0730.zoom.desensitize.core.annotation.FieldMask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前用户信息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/9
 */
@Schema(description = "当前用户信息")
@Data
public class UserInfoVO implements Serializable {
    @Schema(description = "用户ID")
    private String id;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像")
    private String avatar;
    @FieldMask(MaskEnums.EMAIL)
    @Schema(description = "用户邮箱")
    private String email;
    @FieldMask(MaskEnums.Mobile)
    @Schema(description = "用户手机")
    private String phone;
    @Schema(description = "用户角色")
    private List<String> roles;
    @Schema(description = "用户权限")
    private List<String> perms;
}
