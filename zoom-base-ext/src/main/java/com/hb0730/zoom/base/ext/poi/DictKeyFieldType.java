package com.hb0730.zoom.base.ext.poi;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.hb0730.zoom.base.data.Option;
import com.hb0730.zoom.base.ext.services.proxy.SysProxyService;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.poi.annotation.ExcelField;
import com.hb0730.zoom.poi.filedtype.FieldType;

import java.util.List;
import java.util.Optional;

/**
 * 字典类型-导入
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/29
 */
public class DictKeyFieldType implements FieldType {
    private final SysProxyService sysProxyService;


    public DictKeyFieldType() {
        sysProxyService = SpringUtil.getBean(SysProxyService.class);
    }

    @Override
    public Object getValue(String val, ExcelField excelField) {
        if (null == excelField) {
            return val;
        }
        String dictKey = excelField.dictKey();
        if (null == sysProxyService || StrUtil.isBlank(dictKey)) {
            return val;
        }
        // 根据字典key获取字典值
        List<Option> options = sysProxyService.loadItems(dictKey);
        if (null == options || options.isEmpty()) {
            return val;
        }
        Optional<Option> optional = options.stream().filter(e -> e.getLabel().equals(val)).findFirst();
        return optional.map(Option::getValue).orElse(val);
    }

    @Override
    public String setValue(Object val, ExcelField excelField) {
        if (null == excelField) {
            return Convert.toStr(val, "");
        }
        String dictKey = excelField.dictKey();
        if (null == sysProxyService || StrUtil.isBlank(dictKey)) {
            return Convert.toStr(val, "");
        }
        // 根据字典key获取字典值
        List<Option> options = sysProxyService.loadItems(dictKey);
        if (null == options || options.isEmpty()) {
            return Convert.toStr(val, "");
        }
        Optional<Option> optional = options.stream().filter(e -> e.getValue().equals(val)).findFirst();
        return optional.filter(option -> !StrUtil.isEmpty(option.getLabel()))
                .map(Option::getLabel)
                .orElse(Convert.toStr(val, ""));
    }
}
