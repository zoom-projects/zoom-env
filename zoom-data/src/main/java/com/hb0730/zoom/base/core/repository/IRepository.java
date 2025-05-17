package com.hb0730.zoom.base.core.repository;

import com.hb0730.zoom.base.core.IDeleteService;
import com.hb0730.zoom.base.core.IQueryService;
import com.hb0730.zoom.base.core.ISaveService;
import com.hb0730.zoom.base.core.IUpdateService;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;

import java.io.Serializable;

/**
 * Repository interface.
 *
 * @param <Id>        id type
 * @param <Q>         query type
 * @param <CreateReq> create request type
 * @param <UpdateReq> update request type
 * @param <V>         view type
 * @param <E>         entity type
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/17
 * @since 1.0.0
 */
public interface IRepository<Id extends Serializable,
        Q extends PageRequest,
        CreateReq extends Serializable,
        UpdateReq extends Serializable,
        V extends Serializable,
        E extends Serializable> extends IQueryService<Id, Q, V, E>, ISaveService<CreateReq, V, E>, IUpdateService<Id,
        UpdateReq, V, E>, IDeleteService<Id> {
}
