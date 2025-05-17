package com.hb0730.zoom.base.core.service;

import com.hb0730.zoom.base.entity.BaseEntity;
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
public interface IService<
        Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable>
        extends
        IQueryService<Id, Q, V>,
        ISaveService<CreateReq, V>,
        IUpdateService<Id, UpdateReq, V> {

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
}
