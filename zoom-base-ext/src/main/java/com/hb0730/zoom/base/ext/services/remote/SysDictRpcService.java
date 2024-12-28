package com.hb0730.zoom.base.ext.services.remote;

import com.hb0730.zoom.sofa.rpc.core.annotation.RpcAppName;

import java.util.List;

/**
 * 系统字典rpc服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/10
 */
@RpcAppName("zoom-app")
public interface SysDictRpcService {

    /**
     * 获取字典项
     *
     * @param dictCode 字典编码
     * @return 字典项
     */
    List<Object> getDictItems(String dictCode);
}
