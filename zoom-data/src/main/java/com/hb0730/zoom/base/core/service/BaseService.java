package com.hb0730.zoom.base.core.service;

import com.hb0730.zoom.base.core.repository.IRepository;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基础服务类
 *
 * @param <Id>         主键类型
 * @param <Q>          查询类型
 * @param <V>          视图类型
 * @param <E>          实体类型
 * @param <CreateReq>  创建请求类型
 * @param <UpdateReq>  更新请求类型
 * @param <Repository> 仓储类型
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 * @since 1.0.0
 */
public class BaseService<Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends Serializable,
        CreateReq extends Serializable,
        UpdateReq extends Serializable,
        Repository extends IRepository<Id, Q, CreateReq, UpdateReq, V, E>> implements IService<Id, Q, CreateReq, UpdateReq, V, E> {

    @Autowired
    protected Repository repository;


    public Repository getRepository() {
        return repository;
    }

    @Override
    public boolean deleteById(Id id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean deleteByIds(Collection<Id> ids) {
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
    public List<E> listEntity(Q query) {
        return repository.listEntity(query);
    }

    @Override
    public List<E> listEntity() {
        return repository.listEntity();
    }

    @Override
    public List<E> listByIds(Collection<? extends Serializable> ids) {
        return repository.listByIds(ids);
    }

    @Override
    public V get(Id id) {
        return repository.get(id);
    }

    @Override
    public E getById(Id id) {
        return repository.getById(id);
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
    public boolean save(E entity) {
        return repository.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<E> entities) {
        return repository.saveBatch(entities);
    }

    @Override
    public boolean updateById(Id id, UpdateReq updateReq) {
        return repository.updateById(id, updateReq);
    }

    @Override
    public boolean updateById(E entity) {
        return repository.updateById(entity);
    }

    @Override
    public V updateReturnById(Id id, UpdateReq updateReq) {
        return repository.updateReturnById(id, updateReq);
    }

    @Override
    public V updateReturnById(E entity) {
        return repository.updateReturnById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<E> entities) {
        return repository.updateBatchById(entities);
    }
}
