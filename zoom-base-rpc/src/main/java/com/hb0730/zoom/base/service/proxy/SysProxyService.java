package com.hb0730.zoom.base.service.proxy;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.service.dto.SaveMessageDTO;
import com.hb0730.zoom.base.service.remote.SysNotifyRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 系统消息发送RPC代理
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Service
public class SysProxyService implements SysNotifyRpcService {

    @Autowired
    private SysNotifyRpcService notifyRpcService;


    @Override
    public R<?> doSendMessages(Map<String, String> params) {
        return notifyRpcService.doSendMessages(params);
    }

    @Override
    public R<?> saveMessage(SaveMessageDTO dto) {
        return notifyRpcService.saveMessage(dto);
    }
}
