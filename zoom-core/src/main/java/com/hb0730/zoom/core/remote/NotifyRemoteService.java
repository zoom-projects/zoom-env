package com.hb0730.zoom.core.remote;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.ext.services.remote.SysNotifyRpcService;
import com.hb0730.zoom.base.sys.message.entity.SysMessage;
import com.hb0730.zoom.base.sys.message.service.MessageService;
import com.hb0730.zoom.sofa.rpc.core.annotation.RemoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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
        // 1.读取消息中心数据，只查询未发送的和发送失败不超过次数的
        List<SysMessage> messages = messageService.getPendingMessages(params);
        // 2.根据不同的类型走不通的发送实现类
        return messageService.doSendMessage(messages);
    }

    @Override
    public R<?> saveMessage(SaveMessageDTO dto) {
        return messageService.saveMessage(dto);
    }
}
