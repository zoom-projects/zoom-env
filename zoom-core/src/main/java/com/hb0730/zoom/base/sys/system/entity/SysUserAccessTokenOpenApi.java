package com.hb0730.zoom.base.sys.system.entity;

import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户访问令牌-开放接口
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserAccessTokenOpenApi extends BaseEntity {
    /**
     * 用户ID
     */
    private String openApiId;
    /**
     * 访问令牌ID
     */
    private String accessTokenId;
    /**
     * 访问令牌
     */
    private String accessToken;
}
