package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.service.superclass.impl.SuperServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysOperatorLog;
import com.hb0730.zoom.operator.log.core.model.OperatorLogModel;
import com.hb0730.zoom.sys.biz.system.convert.SysOperatorLogConvert;
import com.hb0730.zoom.sys.biz.system.mapper.SysOperatorLogMapper;
import com.hb0730.zoom.sys.biz.system.model.request.operator.log.SysOperatorLogCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.operator.log.SysOperatorLogQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysOperatorLogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/11
 */
@Service
@Slf4j
public class SysOperatorLogService extends SuperServiceImpl<String, SysOperatorLogQueryRequest, SysOperatorLogVO,
        SysOperatorLog, SysOperatorLogCreateRequest, SysOperatorLogCreateRequest, SysOperatorLogMapper, SysOperatorLogConvert> {
    @Autowired
    private SysOperatorLogConvert convert;

    /**
     * 插入操作日志
     *
     * @param model 操作日志
     */
    public void insertOperatorLog(OperatorLogModel model) {
        SysOperatorLog log = convert.convert(model);
        save(log);
    }
}
