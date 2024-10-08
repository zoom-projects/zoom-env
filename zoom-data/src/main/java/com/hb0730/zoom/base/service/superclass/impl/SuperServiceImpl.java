package com.hb0730.zoom.base.service.superclass.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.service.superclass.ISuperService;
import com.hb0730.zoom.mybatis.query.doamin.PageParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * 业务基础实现
 *
 * @param <ID> 主键
 * @param <E>  实体
 * @param <M>  mapper
 * @param <D>  DTO
 * @param <Q>  查询参数
 * @param <C>  mapstruct
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
public class SuperServiceImpl<
        ID extends Serializable,
        E extends BaseEntity,
        M extends BaseMapper<E>,
        V extends Serializable,
        D,
        Q extends PageParams,
        C extends BaseMapstruct<V, D, E>> extends ServiceImpl<M, E> implements ISuperService<ID, E, V, D, Q> {
    @Autowired
    protected C mapstruct;

    //
    @Override
    public IPage<D> pageD(Q query) {
        QueryWrapper<E> queryWrapper = queryWrapper(query);
        IPage<E> page = new Page<>(query.getCurrent(), query.getSize());
        page = this.page(page, queryWrapper);
        return page.convert(mapstruct::entityToDto);
    }

    @Override
    public com.hb0730.zoom.base.data.Page<V> pageV(Q query) {
        QueryWrapper<E> queryWrapper = queryWrapper(query);
        IPage<E> page = new Page<>(query.getCurrent(), query.getSize());
        page = this.page(page, queryWrapper);
        return com.hb0730.zoom.base.data.Page.of(page, mapstruct.entityListToVoList(page.getRecords()));
    }

    @Override
    public List<D> listD(Q query) {
        QueryWrapper<E> queryWrapper = queryWrapper(query);
        List<E> list = this.list(queryWrapper);
        return mapstruct.entityListToDtoList(list);
    }

    @Override
    public List<V> listV(Q query) {
        QueryWrapper<E> queryWrapper = queryWrapper(query);
        List<E> list = this.list(queryWrapper);
        return mapstruct.entityListToVoList(list);
    }

    @Override
    public D getD(ID id) {
        E entity = this.getById(id);
        return mapstruct.entityToDto(entity);
    }

    @Override
    public V getV(ID id) {
        E entity = this.getById(id);
        return mapstruct.entityToVo(entity);
    }


    @Override
    public boolean saveD(D dto) {
        E entity = mapstruct.dtoToEntity(dto);
        return this.save(entity);
    }

    @Override
    public boolean updateD(ID id, D dto) {
        E entity = mapstruct.dtoToEntity(dto);
        entity.setId((String) id);
        return this.updateById(entity);
    }

    /**
     * 获取mapstruct
     *
     * @return mapstruct
     */
    public C getMapstruct() {
        return mapstruct;
    }
}
