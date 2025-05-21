package com.hb0730.zoom.base.service.superclass.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.base.service.superclass.ISuperService;
import com.hb0730.zoom.mybatis.query.QueryHelper;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 通用服务实现类，提供了基本的 CRUD 操作和分页查询功能。
 * 请使用{@link com.hb0730.zoom.base.core.service.BaseService}
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
@Deprecated
public class SuperServiceImpl<Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable,
        M extends BaseMapper<E>,
        C extends BizMapstruct<V, E, CreateReq, UpdateReq>
        > extends ServiceImpl<M, E> implements ISuperService<Id, Q, V, E, CreateReq, UpdateReq> {
    @Autowired
    protected C mapstruct;


    /**
     * 获取mapstruct
     *
     * @return mapstruct
     */
    public C getMapstruct() {
        return mapstruct;
    }

    @Override
    public boolean deleteById(Id id) {
        return removeById(id);
    }

    @Override
    public boolean deleteByIds(List<Id> ids) {
        return removeByIds(ids);
    }

    @Override
    public boolean deleteByIds(Collection<Id> ids) {
        return removeByIds(ids);
    }

    @Override
    public Page<V> page(Q query) {
        IPage<E> page = QueryHelper.toPage(query);
        page = getBaseMapper().selectPage(page, getQueryWrapper(query));
        List<V> voList = mapstruct.toVoList(page.getRecords());
        return Page.of(page, voList);
    }

    @Override
    public List<V> list(Q query) {
        List<E> list = getBaseMapper().selectList(getQueryWrapper(query));
        return mapstruct.toVoList(list);
    }

    @Override
    public List<E> listEntity(Q query) {
        if (query == null) {
            return null;
        }
        return getBaseMapper().selectList(getQueryWrapper(query));
    }

    @Override
    public List<E> listEntity() {
        return getBaseMapper().selectList(null);
    }

    @Override
    public List<E> listByIds(Collection<? extends Serializable> idList) {
        if (idList == null || idList.isEmpty()) {
            return null;
        }
        return getBaseMapper().selectByIds(idList);
    }

    @Override
    public V get(Id id) {
        E entity = getById(id);
        return mapstruct.toVo(entity);
    }

    @Override
    public E getById(Serializable id) {
        if (id == null) {
            return null;
        }
        return super.getById(id);
    }

    @Override
    public boolean create(CreateReq createReq) {
        E entity = mapstruct.createReqToEntity(createReq);
        return save(entity);
    }

    @Override
    public boolean save(E entity) {
        return super.save(entity);
    }

    @Override
    public V createReturn(CreateReq createReq) {
        E entity = mapstruct.createReqToEntity(createReq);
        if (save(entity)) {
            return mapstruct.toVo(entity);
        }
        return null;
    }

    @Override
    public boolean updateById(Id id, UpdateReq updateReq) {
        E entity = getById(id);
        if (entity == null) {
            return false;
        }
        entity = mapstruct.updateEntity(updateReq, entity);
        return updateById(entity);
    }

    @Override
    public boolean updateById(E entity) {
        return updateById(entity);
    }

    @Override
    public V updateReturnById(Id id, UpdateReq updateReq) {
        E entity = getById(id);
        if (entity == null) {
            return null;
        }
        entity = mapstruct.updateEntity(updateReq, entity);
        updateById(entity);
        return mapstruct.toVo(entity);
    }

    @Override
    public V updateReturnById(E entity) {
        if (updateById(entity)) {
            return mapstruct.toVo(entity);
        }
        return null;
    }

    @Override
    public boolean updateBatchById(Collection<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return false;
        }
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean saveBatch(Collection<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return false;
        }
        return super.saveBatch(entityList);
    }
}

