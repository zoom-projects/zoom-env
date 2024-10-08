package com.hb0730.zoom.base.service.superclass;

/**
 * 保存服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
public interface ISaveService<D> {

    /**
     * 保存
     *
     * @param dto dto
     * @return 是否成功
     */
    boolean saveD(D dto);
}
