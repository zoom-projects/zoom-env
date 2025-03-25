package com.hb0730.zoom.remote;

import com.hb0730.zoom.base.data.Domain;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.base.service.superclass.ISuperService;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/22
 */
public class SuperRemoteService<Id extends Serializable, Q extends PageRequest,
        V extends Domain, CreateReq extends Domain, UpdateReq extends Domain,
        E extends BaseEntity,
        Service extends ISuperService<Id, Q, V, E, CreateReq, UpdateReq>> implements IRemoteService<Id, Q, V, CreateReq
        , UpdateReq> {

    @Autowired
    protected Service service;

    @Override
    public Page<V> page(Q query) {
        return service.page(query);
    }

    @Override
    public List<V> list(Q query) {
        return service.list(query);
    }

    @Override
    public boolean create(CreateReq createReq) {
        return service.create(createReq);
    }

    @Override
    public V createReturn(CreateReq createReq) {
        return service.createReturn(createReq);
    }

    @Override
    public boolean updateById(Id id, UpdateReq updateReq) {
        return service.updateById(id, updateReq);
    }

    @Override
    public V updateReturnById(Id id, UpdateReq updateReq) {
        return service.updateReturnById(id, updateReq);
    }

    @Override
    public V get(Id id) {
        return service.get(id);
    }

    @Override
    public boolean deleteById(Id id) {
        return service.deleteById(id);
    }

    @Override
    public boolean deleteByIds(List<Id> ids) {
        return service.deleteByIds(ids);
    }
}
