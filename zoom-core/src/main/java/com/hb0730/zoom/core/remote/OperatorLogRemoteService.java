package com.hb0730.zoom.core.remote;

import com.hb0730.zoom.base.ext.services.dto.SaveOperatorLogDTO;
import com.hb0730.zoom.base.ext.services.remote.SysOperatorLogRpcService;
import com.hb0730.zoom.base.sys.system.entity.SysOperatorLog;
import com.hb0730.zoom.base.sys.system.service.OperatorLogService;
import com.hb0730.zoom.core.convert.OperatorLogConvert;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/6
 */
@Slf4j
@RemoteService
public class OperatorLogRemoteService implements SysOperatorLogRpcService {
    @Autowired
    private OperatorLogConvert operatorLogConvert;
    @Autowired
    private OperatorLogService operatorLogService;

    @Override
    public void saveOperatorLog(SaveOperatorLogDTO dto) {
        SysOperatorLog entity = operatorLogConvert.toEntity(dto);
        operatorLogService.save(entity);
    }
}
