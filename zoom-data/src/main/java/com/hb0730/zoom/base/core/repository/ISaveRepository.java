package com.hb0730.zoom.base.core.repository;

import java.io.Serializable;

/**
 * 保存 Repository
 *
 * @param <Req> 请求参数
 * @param <V>   返回值
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 */
public interface ISaveRepository<Req extends Serializable, V extends Serializable> {
    /**
     * 保存
     *
     * @param req 请求参数
     * @return 是否成功
     */
    default boolean create(Req req) {
        return false;
    }

    /**
     * 保存
     *
     * @param req 请求参数
     * @return 是否成功
     */
    default V createReturn(Req req) {
        return null;
    }
}
