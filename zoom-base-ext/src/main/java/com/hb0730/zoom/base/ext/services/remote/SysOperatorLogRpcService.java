package com.hb0730.zoom.base.ext.services.remote;

import com.hb0730.zoom.base.ext.services.dto.SaveOperatorLogDTO;
import com.hb0730.zoom.sofa.rpc.core.annotation.RpcAppName;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/6
 */
@RpcAppName("zoom-app")
public interface SysOperatorLogRpcService {
    /**
     * 保存操作日志
     *
     * @param dto 操作日志
     */
    void saveOperatorLog(SaveOperatorLogDTO dto);
}
