package com.hb0730.zoom.base.sys.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.hb0730.zoom.biz.entity.ZoomBizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户表
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser extends ZoomBizEntity {
    /**
     * 用户账号
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 加密盐
     */
    private String salt;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 用户状态 0-禁用 1-启用
     */
    private Integer status;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 删除标记
     */
    @TableLogic
    @TableField(value = "del_flag")
    private Integer delFlag;
}
