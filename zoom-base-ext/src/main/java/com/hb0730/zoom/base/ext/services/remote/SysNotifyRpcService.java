package com.hb0730.zoom.base.ext.services.remote;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.sofa.rpc.core.annotation.RpcAppName;

import java.util.Map;

/**
 * 系统通知rpc服务
 * =================================================================================
 * 实现 zoom-core#NotifyRemoteService
 * 代理 zoom-base-rpc#SysProxyService
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@RpcAppName("zoom-app")
public interface SysNotifyRpcService {

    /**
     * 发送消息
     *
     * @param params 参数
     * @return 处理结果
     */
    R<?> doSendMessages(Map<String, String> params);

    /**
     * 保存消息
     *
     * @param dto 保存消息
     * @return 处理结果
     */
    R<?> saveMessage(SaveMessageDTO dto);
}
