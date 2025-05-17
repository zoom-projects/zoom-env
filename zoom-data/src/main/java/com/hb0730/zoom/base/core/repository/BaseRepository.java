package com.hb0730.zoom.base.core.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.base.mapstruct.BizMapstruct;
import com.hb0730.zoom.mybatis.query.QueryHelper;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 基础 Repository
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/12
 */

public class BaseRepository<Id extends Serializable,
        Q extends PageRequest,
        V extends Serializable,
        E extends BaseEntity,
        CreateReq extends Serializable,
        UpdateReq extends Serializable,
        M extends BaseMapper<E>,
        C extends BizMapstruct<V, E, CreateReq, UpdateReq>
        > extends CrudRepository<M, E> implements IRepository<Id, Q, V, E, CreateReq, UpdateReq> {
    @Autowired
    protected C mapstruct;

    @Override
    public Page<V> page(Q query) {
        IPage<E> page = QueryHelper.toPage(query);
        page = getBaseMapper().selectPage(page, getQueryWrapper(query));
        List<V> voList = mapstruct.toVoList(page.getRecords());
        return Page.of(page, voList);
    }

    @Override
    public List<V> list(Q query) {
        List<E> list = list(getQueryWrapper(query));
        return mapstruct.toVoList(list);
    }

    @Override
    public V get(Id id) {
        E entity = getById(id);
        return mapstruct.toVo(entity);
    }


    @Override
    public boolean create(CreateReq createReq) {
        E entity = mapstruct.createReqToEntity(createReq);
        return save(entity);
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
    public V updateReturnById(Id id, UpdateReq updateReq) {
        E entity = getById(id);
        if (entity == null) {
            return null;
        }
        entity = mapstruct.updateEntity(updateReq, entity);
        updateById(entity);
        return mapstruct.toVo(entity);
    }

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
}
