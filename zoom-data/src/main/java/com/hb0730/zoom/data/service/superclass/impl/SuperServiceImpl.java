package com.hb0730.zoom.data.service.superclass.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.data.entity.BaseEntity;
import com.hb0730.zoom.data.service.superclass.ISuperService;
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
        D,
        Q extends PageParams,
        C extends BaseMapstruct<D, E>> extends ServiceImpl<M, E> implements ISuperService<ID, E, D, Q> {
    @Autowired
    protected C mapstruct;

    //
    @Override
    public IPage<D> page(Q query) {
        QueryWrapper<E> queryWrapper = queryWrapper(query);
        IPage<E> page = new Page<>(query.getCurrent(), query.getSize());
        page = this.page(page, queryWrapper);
        return page.convert(mapstruct::toDto);
    }

    @Override
    public List<D> list(Q query) {
        QueryWrapper<E> queryWrapper = queryWrapper(query);
        List<E> list = this.list(queryWrapper);
        return mapstruct.toDtoList(list);
    }

    @Override
    public boolean dtoSave(D dto) {
        E entity = mapstruct.toEntity(dto);
        return this.save(entity);
    }

    @Override
    public boolean dtoUpdate(ID id, D dto) {
        E entity = mapstruct.toEntity(dto);
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
