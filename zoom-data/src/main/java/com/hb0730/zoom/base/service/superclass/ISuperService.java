package com.hb0730.zoom.base.service.superclass;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.hb0730.zoom.base.core.service.IService;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.QueryHelper;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @param <Id>        id
 * @param <Q>         query
 * @param <V>         view
 * @param <E>         entity
 * @param <CreateReq> create request
 * @param <UpdateReq> update request
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
@Deprecated
public interface ISuperService<
        Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable>
        extends
        IService<Id, Q, CreateReq, UpdateReq, V, E> {

    /**
     * delete by id
     *
     * @param id .
     * @return .
     */
    boolean deleteById(Id id);

    /**
     * batch delete by id
     *
     * @param ids .
     * @return .
     */
    boolean deleteByIds(List<Id> ids);

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
