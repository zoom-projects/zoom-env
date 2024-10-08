package com.hb0730.zoom.base.service.superclass;

import java.io.Serializable;

/**
 * 更新服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
public interface IUpdateService<Id extends Serializable, D> {
    /**
     * 更新
     *
     * @param id  id
     * @param dto dto
     * @return 是否成功
     */
    boolean updateD(Id id, D dto);
}
