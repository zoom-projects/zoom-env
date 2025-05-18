package com.hb0730.zoom.base.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * @param <Id>        id type
 * @param <UpdateReq> update request type
 * @param <V>         view type
 * @param <E>         entity type
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/17
 * @since 1.0.0
 */
public interface IUpdateService<Id extends Serializable, UpdateReq extends Serializable, V extends Serializable, E extends Serializable> {
    /**
     * update
     *
     * @param id  id
     * @param req request parameter
     * @return true if success, false otherwise
     */
    boolean updateById(Id id, UpdateReq req);

    /**
     * update entity
     *
     * @param entity entity to update
     * @return true if success, false otherwise
     */
    boolean updateById(E entity);

    /**
     * update and return
     *
     * @param id  id
     * @param req request parameter
     * @return updated entity
     */
    V updateReturnById(Id id, UpdateReq req);

    /**
     * update entity and return
     *
     * @param entity entity to update
     * @return updated entity
     */
    V updateReturnById(E entity);

    /**
     * update batch by id
     *
     * @param entities entities to update
     * @return true if success, false otherwise
     */
    boolean updateBatchById(Collection<E> entities);
}
