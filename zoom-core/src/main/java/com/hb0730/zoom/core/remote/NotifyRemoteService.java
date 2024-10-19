package com.hb0730.zoom.core.remote;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.service.dto.SaveMessageDTO;
import com.hb0730.zoom.base.service.remote.SysNotifyRpcService;
import com.hb0730.zoom.base.sys.notifty.service.MessageService;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Slf4j
@RemoteService
public class NotifyRemoteService implements SysNotifyRpcService {
    @Autowired
    MessageService messageService;

    @Override
    public R<?> doSendMessages(Map<String, String> params) {
        return messageService.doSendMessage(null);
    }

    @Override
    public R<?> saveMessage(SaveMessageDTO dto) {
        return messageService.saveMessage(dto);
    }
}
