package com.hb0730.zoom.base.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hb0730.zoom.base.entity.BaseEntity;
import com.hb0730.zoom.base.mapstruct.BaseMapstruct;
import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.mybatis.query.doamin.PageParams;

import java.io.Serializable;

/**
 * 业务基础实现-所有的service应当继承此类
 *
 * @param <ID> 主键
 * @param <E>  实体
 * @param <M>  mapper
 * @param <D>  DTO
 * @param <C>  mapstruct 请作为Spring Bean
 * @param <Q>  查询参数
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 * @see SuperServiceImpl
 */
public class BaseService<ID extends Serializable, E extends BaseEntity, M extends BaseMapper<E>, V extends Serializable, D,
        Q extends PageParams,
        C extends BaseMapstruct<V, D, E>> extends SuperServiceImpl<ID, E, M, V, D, Q, C> {
}
