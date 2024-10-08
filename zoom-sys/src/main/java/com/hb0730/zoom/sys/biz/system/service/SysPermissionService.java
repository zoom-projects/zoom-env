package com.hb0730.zoom.sys.biz.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.enums.MenuTypeEnums;
import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.service.BaseService;
import com.hb0730.zoom.base.sys.system.entity.SysPermission;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.convert.SysPermissionConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysPermissionMapper;
import com.hb0730.zoom.sys.biz.system.model.dto.SysPermissionDTO;
import com.hb0730.zoom.sys.biz.system.model.request.SysPermissionQuery;
import com.hb0730.zoom.sys.biz.system.model.request.SysPermissionTreeQuery;
import com.hb0730.zoom.sys.biz.system.model.vo.SysPermissionTreeVO;
import com.hb0730.zoom.sys.biz.system.model.vo.SysPermissionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单与权限
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/3
 */
@Service
@Slf4j
public class SysPermissionService extends BaseService<
        String,
        SysPermission,
        SysPermissionMapper,
        SysPermissionVO,
        SysPermissionDTO,
        SysPermissionQuery,
        SysPermissionConvert> {

    /**
     * 菜单树
     *
     * @return 菜单树
     */
    public List<SysPermissionTreeVO> tree(SysPermissionTreeQuery query) {
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.lambdaQuery(SysPermission.class)
                .orderByAsc(SysPermission::getSort);

        List<SysPermission> tree = baseMapper.selectList(queryWrapper);
        return mapstruct.toTreeVo(tree);
    }

    /**
     * 根据用户id获取权限
     *
     * @param userId 用户id
     * @return 权限
     */
    public List<SysPermission> findByUserId(String userId) {
        return baseMapper.findByUserId(userId);
    }

    /**
     * 保存
     *
     * @param dto dto
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveD(SysPermissionDTO dto) {
        check(dto);
        String parentId = dto.getParentId();
        if (StrUtil.isBlank(parentId)) {
            dto.setParentId(null);
        }
        dto.setLeaf(true);
        SysPermission entity = getMapstruct().dtoToEntity(dto);
        save(entity);
        if (StrUtil.isNotBlank(parentId)) {
            this.baseMapper.changeLeafById(parentId, 0);
        }
        return true;
    }


    /**
     * 更新
     *
     * @param id  id
     * @param dto dto
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateD(String id, SysPermissionDTO dto) {
        check(dto);
        SysPermission entity = getMapstruct().dtoToEntity(dto);
        entity.setId(id);
        // steps 1: 更新parentId
        if (StrUtil.isBlank(dto.getParentId())) {
            entity.setParentId(null);
        }
        // steps 2: 更新是否是叶子节点
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.lambdaQuery(SysPermission.class)
                .eq(SysPermission::getParentId, dto.getId());
        Long count = baseMapper.of(queryWrapper).count();
        entity.setIsLeaf(count == 0 ? 1 : 0);

        // 如果当前菜单的父菜单变了，则需要修改新父菜单和老父菜单的，叶子节点状态
        SysPermission permission = getById(id);
        String parentId = permission.getParentId();
        String pId = entity.getParentId();
        if ((StrUtil.isNotBlank(pId) && !pId.equals(parentId)) || StrUtil.isBlank(pId) && StrUtil.isNotBlank(parentId)) {
            // 1.设置新的父菜单不为叶子节点
            baseMapper.changeLeafById(pId, 0);
            // 2.判断老的父菜单是否还有子菜单
            LambdaQueryWrapper<SysPermission> query = Wrappers.lambdaQuery(SysPermission.class)
                    .eq(SysPermission::getParentId, parentId);
            Long count1 = baseMapper.of(query).count();
            if (count1 == 1) {
                if (StrUtil.isNotBlank(parentId)) {
                    baseMapper.changeLeafById(parentId, 1);
                }
            }
        }
        return updateById(entity);
    }

    /**
     * 删除
     *
     * @param id id
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermission(String id) {
        SysPermission permission = getById(id);
        if (permission == null) {
            return false;
        }
        String parentId = permission.getParentId();
        if (StrUtil.isNotBlank(parentId)) {
            LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.lambdaQuery(SysPermission.class)
                    .eq(SysPermission::getParentId, parentId);
            Long count = baseMapper.of(queryWrapper).count();
            if (count == 1) {
                baseMapper.changeLeafById(parentId, 1);
            }
        }
        boolean res = removeById(id);
        // 删除子节点
        removeChildrenBy(id);
        return res;
    }


    /**
     * 删除子节点
     *
     * @param parentId 父级id
     */
    private void removeChildrenBy(String parentId) {
        LambdaQueryWrapper<SysPermission> queryWrapper = Wrappers.lambdaQuery(SysPermission.class)
                .eq(SysPermission::getParentId, parentId);
        List<SysPermission> list = baseMapper.of(queryWrapper).list();
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(item -> {
                removeChildrenBy(item.getId());
                removeById(item.getId());
            });
        }
    }

    /**
     * 链式检测
     *
     * @param dto dto
     */
    public void check(SysPermissionDTO dto) {
        Integer menuType = dto.getMenuType();
        MenuTypeEnums menuTypeEnums = PairEnum.of(MenuTypeEnums.class, menuType)
                .orElseThrow(() -> new ZoomException("菜单类型错误"));
        switch (menuTypeEnums) {
            case MENU:
                checkMenu(dto);
                break;
            case BUTTON:
                checkButton(dto);
                break;
            case EXTERNAL:
                checkLink(dto);
                break;
            case FRAME:
                checkIframe(dto);
                break;
            default:
                throw new ZoomException("菜单类型错误");
        }
    }

    /**
     * 类型为：菜单，参数检测
     */
    public void checkMenu(SysPermissionDTO dto) {
        String title = dto.getTitle();
        if (title == null || title.isEmpty()) {
            throw new ZoomException("菜单名称不能为空");
        }
        String routeName = dto.getRouteName();
        if (routeName == null || routeName.isEmpty()) {
            throw new ZoomException("路由名称不能为空");
        }
        String routePath = dto.getRoutePath();
        if (routePath == null || routePath.isEmpty()) {
            throw new ZoomException("路由路径不能为空");
        }

    }

    /**
     * 类型为：按钮，参数检测
     */
    public void checkButton(SysPermissionDTO dto) {
        String title = dto.getTitle();
        if (title == null || title.isEmpty()) {
            throw new ZoomException("按钮名称不能为空");
        }
        String perms = dto.getPerms();
        if (perms == null || perms.isEmpty()) {
            throw new ZoomException("按钮权限不能为空");
        }

    }

    /**
     * 类型为：外链，参数检测
     */
    public void checkLink(SysPermissionDTO dto) {
        String title = dto.getTitle();
        if (title == null || title.isEmpty()) {
            throw new ZoomException("外链名称不能为空");
        }
    }

    /**
     * 类型为：iframe，参数检测
     */
    public void checkIframe(SysPermissionDTO dto) {
        String title = dto.getTitle();
        if (title == null || title.isEmpty()) {
            throw new ZoomException("iframe名称不能为空");
        }
        String frameSrc = dto.getFrameSrc();
        if (frameSrc == null || frameSrc.isEmpty()) {
            throw new ZoomException("iframe路径不能为空");
        }

    }
}
