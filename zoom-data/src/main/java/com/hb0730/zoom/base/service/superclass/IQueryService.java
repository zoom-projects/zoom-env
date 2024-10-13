package com.hb0730.zoom.base.service.superclass;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.QueryHelper;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
public interface IQueryService<
        Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity> {
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    default Page<V> page(Q query) {
        return Page.empty();
    }

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 数据
     */
    default List<V> list(Q query) {
        return null;
    }

    /**
     * 查询
     *
     * @param id id
     * @return 数据
     */
    default V get(Id id) {
        return null;
    }

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 数据
     */
    default Wrapper<E> getQueryWrapper(Q query) {
        return QueryHelper.ofBean(query);
    }
}
