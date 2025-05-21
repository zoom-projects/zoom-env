package com.hb0730.zoom.base.core.repository;

import com.hb0730.zoom.base.core.IBasicDeleteService;
import com.hb0730.zoom.base.core.IBasicQueryService;
import com.hb0730.zoom.base.core.IBasicSaveService;
import com.hb0730.zoom.base.core.IBasicUpdateService;

import java.io.Serializable;

/**
 * Repository interface.
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
public interface IBasicRepository<Id extends Serializable, E extends Serializable> extends IBasicQueryService<Id, E>,
        IBasicSaveService<E>,
        IBasicUpdateService<E>, IBasicDeleteService<Id> {
    /**
     * Default batch size for batch operations.
     */
    int DEFAULT_BATCH_SIZE = 1000;
}
