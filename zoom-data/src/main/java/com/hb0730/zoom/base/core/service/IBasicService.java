package com.hb0730.zoom.base.core.service;

import com.hb0730.zoom.base.core.IBasicDeleteService;
import com.hb0730.zoom.base.core.IBasicQueryService;
import com.hb0730.zoom.base.core.IBasicSaveService;
import com.hb0730.zoom.base.core.IBasicUpdateService;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
public interface IBasicService<Id extends Serializable, E extends Serializable> extends IBasicQueryService<Id, E>,
        IBasicSaveService<E>, IBasicUpdateService<E>, IBasicDeleteService<Id> {

    /**
     * Save or update entity.
     *
     * @param entity entity
     * @return true if success, false otherwise
     */
    boolean saveOrUpdate(E entity);
}
