package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.core.service.BaseService;
import com.hb0730.zoom.base.sys.system.entity.SysDictItem;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysDictItemVO;
import com.hb0730.zoom.sys.biz.system.repository.SysDictItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/12
 */
@Service
@Slf4j
public class SysDictItemService extends BaseService<String, SysDictItemQueryRequest, SysDictItemVO, SysDictItem,
        SysDictItemCreateRequest, SysDictItemCreateRequest,
        SysDictItemRepository> {


    /**
     * 根据字典id查询字典项,并按排序升序排列
     *
     * @param dictId 字典id
     * @return 字典项列表
     */
    public List<SysDictItem> listByDictIdOrderBySortAsc(String dictId) {
        return listByDictIdOrderBySort(dictId, true);
    }

    /**
     * 根据字典id查询字典项,并按排序升序或降序排列
     *
     * @param dictId 字典id
     * @param isAsc  true 升序 false 降序
     * @return 字典项列表
     */
    public List<SysDictItem> listByDictIdOrderBySort(String dictId, boolean isAsc) {
        return repository.listByDictIdOrderBySort(dictId, isAsc);
    }

}
