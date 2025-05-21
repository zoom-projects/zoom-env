package com.hb0730.zoom.sys.biz.system.repository;

import com.hb0730.zoom.sys.biz.system.repository.mapper.SysSerialNumberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/5/21
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysSerialNumberRepository {
    private final SysSerialNumberMapper serialNumberMapper;

    /**
     * 获取任务单号
     *
     * @param params 参数
     * @return 任务单号
     */
    public Map<String, String> getSerialNumber(Map<String, String> params) {
        serialNumberMapper.getSerialnumber(params);
        return params;
    }
}
