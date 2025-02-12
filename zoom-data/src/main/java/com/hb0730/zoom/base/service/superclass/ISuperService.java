package com.hb0730.zoom.base.service.superclass;

import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;

import java.io.Serializable;

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
public interface ISuperService<
        Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable>
        extends
        IQueryService<Id, Q, V, E>,
        ISaveService<CreateReq, V>,
        IUpdateService<Id, UpdateReq, V> {

    /**
     * delete by id
     *
     * @param id .
     * @return .
     */
    boolean deleteById(Id id);
}
