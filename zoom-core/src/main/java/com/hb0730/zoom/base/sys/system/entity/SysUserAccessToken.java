package com.hb0730.zoom.base.sys.system.entity;

import com.hb0730.zoom.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户访问令牌
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserAccessToken extends BaseEntity {
    /**
     * 名称
     */
    private String name;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态
     */
    private Boolean status = true;
}
