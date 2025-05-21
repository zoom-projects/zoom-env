package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.exception.ZoomException;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.sys.biz.system.repository.SysSerialNumberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/27
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysSerialNumberService {
    private final SysSerialNumberRepository repository;


    /**
     * 采番序列号取得
     *
     * @param category 采番号类别
     * @param prefix   前缀
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String getSerialNumber(String category, String prefix) {
        Map<String, String> map = new HashMap<>();
        map.put("p_CATEGORY", category);
        map.put("p_PREFIX", prefix);
        map = repository.getSerialNumber(map);

        String serialnumber = map.get("p_OUT_SN");
        if (StrUtil.isBlank(serialnumber) || "0".equals(serialnumber)) {
            throw new ZoomException("流水号码取得失败！");
        }

        return serialnumber;
    }
}
