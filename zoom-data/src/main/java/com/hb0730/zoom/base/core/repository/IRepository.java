package com.hb0730.zoom.base.core.repository;

import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 查询、保存、更新 Repository
 *
 * @param <Id>        主键类型
 * @param <Q>         查询条件
 * @param <V>         返回值类型
 * @param <E>         实体类型
 * @param <CreateReq> 创建请求参数
 * @param <UpdateReq> 更新请求参数
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 */
public interface IRepository<
        Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable>
        extends
        IQueryRepository<Id, Q, V, E>,
        ISaveRepository<CreateReq, V>,
        IUpdateRepository<Id, UpdateReq, V> {

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
    boolean deleteByIds(List<Id> ids);
}
