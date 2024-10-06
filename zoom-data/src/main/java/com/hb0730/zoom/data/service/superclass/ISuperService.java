package com.hb0730.zoom.data.service.superclass;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hb0730.zoom.data.entity.BaseEntity;
import com.hb0730.zoom.mybatis.query.doamin.PageParams;

import java.io.Serializable;

/**
 * 业务基础接口
 *
 * @param <Id> 主键
 * @param <E>  实体
 * @param <D>  DTO
 * @param <Q>  查询参数
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/5
 */
public interface ISuperService<Id extends Serializable, E extends BaseEntity, D, Q extends PageParams>
        extends IService<E>, IQueryService<D, Q, E>, IUpdateService<Id, D>, ISaveService<D> {
}
