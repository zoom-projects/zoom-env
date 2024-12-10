package com.hb0730.zoom.base.sys.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.sys.system.entity.SysOperatorLog;
import com.hb0730.zoom.base.sys.system.mapper.OperatorLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/6
 */
@Service
@Slf4j
public class OperatorLogService extends ServiceImpl<OperatorLogMapper, SysOperatorLog> {
}
