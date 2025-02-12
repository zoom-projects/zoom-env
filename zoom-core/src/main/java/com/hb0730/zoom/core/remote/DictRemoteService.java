package com.hb0730.zoom.core.remote;

import com.hb0730.zoom.base.data.Option;
import com.hb0730.zoom.base.ext.services.remote.SysDictRpcService;
import com.hb0730.zoom.base.sys.system.service.DictService;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/10
 */
@Slf4j
@RemoteService
public class DictRemoteService implements SysDictRpcService {
    @Autowired
    DictService dictService;

    @Override
    public List<Object> getDictItems(String dictCode) {
        List<Option> items = dictService.loadItems(dictCode);
        if (CollectionUtil.isEmpty(items)) {
            return null;
        }
        return items.stream().map(Option::getValue).toList();
    }

    @Override
    public List<Option> loadItems(String dictCode) {
        return dictService.loadItems(dictCode);
    }
}
