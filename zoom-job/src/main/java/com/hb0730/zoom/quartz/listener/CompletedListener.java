package com.hb0730.zoom.quartz.listener;

import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.enums.TaskNotifyEnums;
import com.hb0730.zoom.base.enums.TaskTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.ext.services.proxy.SysProxyService;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.quartz.event.CompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/29
 */
@Component
@Async
@Slf4j
public class CompletedListener implements ApplicationListener<CompletedEvent> {
    @Autowired
    private SysProxyService sysProxyService;

    @Override
    public void onApplicationEvent(CompletedEvent event) {
        TaskInfo taskInfo = event.getTaskInfo();
        if (taskInfo == null) {
            return;
        }
        sendMessage(taskInfo);
    }

    void sendMessage(TaskInfo taskInfo) {
        String createdBy = taskInfo.getCreatedBy();
        if (StrUtil.isBlank(createdBy)) {
            return;
        }
        UserInfoDTO username = sysProxyService.findUsername(createdBy);
        if (!sysProxyService.checkUserMessageNotification(username)) {
            log.warn("用户未开启消息通知");
            return;
        }
        String email = username.getEmail();
        if (StrUtil.isBlank(email)) {
            log.warn("用户未设置邮箱");
            return;
        }
        SaveMessageDTO message = new SaveMessageDTO();
        message.setMsgType(MessageTypeEnums.EMAIL);
        message.setTemplateCode(TaskNotifyEnums.COMPLETED.getCode());
        message.setReceiver(username.getEmail());
        Optional<TaskTypeEnums> typeEnums = PairEnum.of(TaskTypeEnums.class, taskInfo.getType());
        if (typeEnums.isPresent()) {
            Map<String, String> map = Map.of("code", String.format("%s(%s)", typeEnums.get().getMessage(), taskInfo.getTaskNum()));
            message.setExtra(map);
        } else
            message.setExtra(Map.of("code", taskInfo.getTaskNum()));
        sysProxyService.saveMessage(message);

    }
}
