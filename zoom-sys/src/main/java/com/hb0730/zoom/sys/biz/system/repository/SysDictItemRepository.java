package com.hb0730.zoom.sys.biz.system.repository;

import com.hb0730.zoom.base.core.repository.BaseRepository;
import com.hb0730.zoom.base.sys.system.entity.SysDictItem;
import com.hb0730.zoom.sys.biz.system.convert.SysDictItemConvert;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.dict.item.SysDictItemQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysDictItemVO;
import com.hb0730.zoom.sys.biz.system.repository.mapper.SysDictItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
public class SysDictItemRepository extends BaseRepository<String, SysDictItemQueryRequest, SysDictItemVO, SysDictItem,
        SysDictItemCreateRequest, SysDictItemCreateRequest,
        SysDictItemMapper, SysDictItemConvert> {

    /**
     * 根据字典id查询字典项,并按排序升序或降序排列
     *
     * @param dictId 字典id
     * @param isAsc  true 升序 false 降序
     * @return 字典项列表
     */
    public List<SysDictItem> listByDictIdOrderBySort(String dictId, boolean isAsc) {
        return baseMapper.of(
                query -> query.eq(
                        SysDictItem::getDictId, dictId
                ).orderBy(
                        true,
                        isAsc,
                        SysDictItem::getSort
                )
        ).list();
    }
}
