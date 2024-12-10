package com.hb0730.zoom.base.sys.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hb0730.zoom.base.data.Option;
import com.hb0730.zoom.base.enums.DictValueTypeEnums;
import com.hb0730.zoom.base.sys.system.entity.SysDict;
import com.hb0730.zoom.base.sys.system.entity.SysDictItem;
import com.hb0730.zoom.base.sys.system.mapper.DictMapper;
import com.hb0730.zoom.base.utils.JsonUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.hb0730.zoom.base.ZoomConst.DICT_ITEMS_KEY;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/5
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DictService extends ServiceImpl<DictMapper, SysDict> {
    private final DictItemService dictItemService;

    /**
     * 加载字典项
     *
     * @param dictCode 字典编码
     * @return 字典项
     */
    @Cacheable(value = DICT_ITEMS_KEY, key = "#dictCode", unless = "#result == null")
    public List<Option> loadItems(String dictCode) {
        SysDict dict = baseMapper.of(
                Wrappers.lambdaQuery(SysDict.class)
                        .eq(SysDict::getDictCode, dictCode)
        ).one();
        if (dict == null) {
            return null;
        }
        LambdaQueryWrapper<SysDictItem> queryWrapper = Wrappers.lambdaQuery(SysDictItem.class)
                .eq(SysDictItem::getDictId, dict.getId())
                .orderByAsc(SysDictItem::getSort);
        List<SysDictItem> dictItems = dictItemService.list(queryWrapper);


        List<Option> optionList = new ArrayList<>(dictItems.size());
        for (SysDictItem dictItem : dictItems) {
            String dictType = dict.getDictType();
            DictValueTypeEnums valueTypeEnums = DictValueTypeEnums.of(dictType);
            Option option = new Option();
            option.setLabel(dictItem.getItemText());
            option.setValue(valueTypeEnums.parse(dictItem.getItemValue()));
            option.setDescription(dictItem.getDescription());
            // 额外参数
            String extra = dictItem.getExtra();
            if (StrUtil.isNotBlank(extra)) {
                Map<String, Object> extraMap = JsonUtil.DEFAULT.json2Obj(extra, new TypeReference<Map<String, Object>>() {
                });
                option.setAttrs(extraMap);
            }
            optionList.add(option);
        }

        return optionList;
    }
}
