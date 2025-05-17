package com.hb0730.zoom.base.core.service;

import com.hb0730.zoom.base.core.repository.IRepository;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;
import java.util.List;

/**
 * 基础服务类
 *
 * @param <Id> 主键类型
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 */
public class BaseService<
        Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable,
        Repository extends IRepository<Id, Q, V, E, CreateReq,
                UpdateReq>> implements IService<Id, Q, V, E, CreateReq, UpdateReq> {
    @Autowired
    @Lazy
    protected Repository repository;


    /**
     * 获取 repository
     *
     * @return .
     */
    public Repository getRepository() {
        return repository;
    }

    @Override
    public boolean deleteById(Id id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean deleteByIds(List<Id> ids) {
        return repository.deleteByIds(ids);
    }

    @Override
    public Page<V> page(Q query) {
        return repository.page(query);
    }

    @Override
    public List<V> list(Q query) {
        return repository.list(query);
    }

    @Override
    public V get(Id id) {
        return repository.get(id);
    }

    @Override
    public boolean create(CreateReq createReq) {
        return repository.create(createReq);
    }

    @Override
    public V createReturn(CreateReq createReq) {
        return repository.createReturn(createReq);
    }

    @Override
    public boolean updateById(Id id, UpdateReq updateReq) {
        return repository.updateById(id, updateReq);
    }

    @Override
    public V updateReturnById(Id id, UpdateReq updateReq) {
        return repository.updateReturnById(id, updateReq);
    }
}
