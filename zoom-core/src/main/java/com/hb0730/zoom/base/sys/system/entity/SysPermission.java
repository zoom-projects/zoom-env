package com.hb0730.zoom.base.sys.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.hb0730.zoom.data.entity.BizEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜单与权限
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysPermission extends BizEntity {
    /**
     * 父级ID
     */
    @TableField(value = "parent_id", updateStrategy = FieldStrategy.ALWAYS, insertStrategy = FieldStrategy.ALWAYS)
    private String parentId;
    /**
     * 是否叶子节点: 1:是 0:不是
     */
    private Integer isLeaf;

    /**
     * 是否叶子节点
     *
     * @return 是否叶子节点
     */
    public boolean getLeaf() {
        return isLeaf != null && isLeaf == 1;
    }
    /*==========================路由信息==========================*/
    /**
     * 路由地址
     */
    private String routePath;
    /**
     * 路由名称（必须保持唯一）
     */
    private String routeName;
    /**
     * 路由重定向
     */
    private String redirect;
    /**
     * 路由组件
     */
    private String component;

    /*==========================菜单信息==========================*/
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 是否隐藏
     */
    private Integer isHidden;

    /**
     * 是否隐藏
     *
     * @return 是否隐藏
     */
    public boolean getHidden() {
        return isHidden != null && isHidden == 1;
    }

    /**
     * 是否缓存,如果缓存，请保持routerName与组件名称一致
     */
    private Integer isKeepAlive;

    /**
     * 是否固定在标签视图
     *
     * @return 是否固定在标签视图
     */
    public boolean getKeepAlive() {
        return isKeepAlive != null && isKeepAlive == 1;
    }

    /**
     * 是否固定在标签视图
     */
    private Integer isAffix;

    /**
     * 是否固定在标签视图
     *
     * @return 是否固定在标签视图
     */
    public boolean getAffix() {
        return isAffix != null && isAffix == 1;
    }

    /**
     * 是否大屏
     */
    private Integer isFullScreen;

    /**
     * 是否大屏
     *
     * @return 是否大屏
     */
    public boolean getFullScreen() {
        return isFullScreen != null && isFullScreen == 1;
    }

    /**
     * iframe
     */
    private String frameSrc;

    /**
     * 菜单类型 0 目录 1 菜单 2 按钮
     *
     * @see com.hb0730.zoom.base.enums.MenuTypeEnums
     */
    private Integer menuType;
    /**
     * 权限标识
     */
    private String perms;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否禁用
     *
     * @return 是否禁用
     */
    public boolean isDisabled() {
        return getStatus() != null && getStatus() == 0;
    }

}
