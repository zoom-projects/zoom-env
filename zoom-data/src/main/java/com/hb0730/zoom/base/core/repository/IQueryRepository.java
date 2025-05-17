package com.hb0730.zoom.base.core.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.QueryHelper;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 查询 Repository
 *
 * @param <Id> 主键类型
 * @param <Q>  查询条件
 * @param <V>  返回值类型
 * @param <E>  实体类型
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 */
public interface IQueryRepository<Id extends Serializable,
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
