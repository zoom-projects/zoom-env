package com.hb0730.zoom.base.service.superclass;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.QueryHelper;
import com.hb0730.zoom.mybatis.query.doamin.PageParams;

import java.io.Serializable;
import java.util.List;

/**
 * 查询服务
 *
 * @param <V> 视图对象
 * @param <D> dto
 * @param <Q> 查询条件
 * @param <E> 实体
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
public interface IQueryService<Id extends Serializable, V extends Serializable, D, Q extends PageParams, E extends BaseEntity> {

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    IPage<D> pageD(Q query);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    Page<V> pageV(Q query);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 列表数据
     */
    List<D> listD(Q query);

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 列表数据
     */
    List<V> listV(Q query);

    /**
     * 根据id查询
     *
     * @param id id
     * @return dto
     */
    D getD(Id id);

    /**
     * 根据id查询
     *
     * @param id id
     * @return 视图对象
     */
    V getV(Id id);

    /**
     * 查询条件
     *
     * @param query 查询条件
     * @return 查询条件
     */
    default QueryWrapper<E> queryWrapper(Q query) {
        return QueryHelper.ofBean(query);
    }
}
