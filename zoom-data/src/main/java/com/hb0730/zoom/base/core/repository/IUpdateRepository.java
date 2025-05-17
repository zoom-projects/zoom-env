package com.hb0730.zoom.base.core.repository;

import java.io.Serializable;

/**
 * 更新 Repository
 *
 * @param <Id>  主键类型
 * @param <Req> 请求参数
 * @param <V>   返回值
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 */
public interface IUpdateRepository<Id extends Serializable, Req extends Serializable, V extends Serializable> {
    /**
     * 根据id更新
     *
     * @param id  id
     * @param req 请求参数
     * @return 是否成功
     */
    default boolean updateById(Id id, Req req) {
        return false;
    }

    /**
     * 根据id更新
     *
     * @param id  id
     * @param req 请求参数
     * @return 是否成功
     */
    default V updateReturnById(Id id, Req req) {
        return null;
    }
}
