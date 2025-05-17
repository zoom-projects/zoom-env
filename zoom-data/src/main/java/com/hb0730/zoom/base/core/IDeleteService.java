package com.hb0730.zoom.base.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Delete service interface.
 *
 * @param <Id> id type
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/17
 * @since 1.0.0
 */
public interface IDeleteService<Id extends Serializable> {
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
    boolean deleteByIds(Collection<Id> ids);
}
