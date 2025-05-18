package com.hb0730.zoom.base.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Save service interface.
 *
 * @param <CreateReq> create request type
 * @param <V>         view type
 * @param <E>         entity type
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/17
 * @since 1.0.0
 */
public interface ISaveService<CreateReq extends Serializable, V extends Serializable, E extends Serializable> {

    /**
     * Save
     *
     * @param req request parameter
     * @return true if success, false otherwise
     */
    boolean create(CreateReq req);

    /**
     * Save and return
     *
     * @param req request parameter
     * @return saved entity
     */
    V createReturn(CreateReq req);

    /**
     * Save entity
     *
     * @param entity entity to save
     * @return true if success, false otherwise
     */
    boolean save(E entity);

    /**
     * batch save
     *
     * @param entities entities to save
     * @return true if success, false otherwise
     */
    boolean saveBatch(Collection<E> entities);
}
