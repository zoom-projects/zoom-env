package com.hb0730.zoom.base.service.superclass;

import java.io.Serializable;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
public interface ISaveService<Req extends Serializable> {

    /**
     * 保存
     *
     * @param req 请求参数
     * @return 是否成功
     */
    default boolean create(Req req) {
        return false;
    }
}
