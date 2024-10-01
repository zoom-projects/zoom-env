package com.hb0730.zoom.biz.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.biz.entity.ZoomBizEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * Service基类
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/23
 */
@Slf4j
public class ZoomBizService<M extends BaseMapper<T>, T extends ZoomBizEntity> extends ServiceImpl<M, T> implements IService<T> {
}
