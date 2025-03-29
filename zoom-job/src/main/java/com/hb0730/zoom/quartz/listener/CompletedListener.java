package com.hb0730.zoom.quartz.listener;

import com.hb0730.zoom.base.PairEnum;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.enums.SubscribeMsgCodeEnums;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/29
 */
@Component
@Async("actuatorTaskExecutor")
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
        if (username == null) {
            return;
        }
        SaveMessageDTO message = new SaveMessageDTO();
        Optional<TaskTypeEnums> typeEnums = PairEnum.of(TaskTypeEnums.class, taskInfo.getType());
        Map<String, String> extra = new HashMap<>();
        if (typeEnums.isPresent()) {
            extra.put("name", typeEnums.get().getMessage());
        } else {
            extra.put("name", "未知");
        }
        extra.put("code", taskInfo.getTaskNum());
        extra.put("complete_time", taskInfo.getDisTimeEnd());
        message.setExtra(extra);


        sysProxyService.saveMessageAllNotice(
                createdBy,
                SubscribeMsgCodeEnums.BASIC_TASK_MSG.getCode(),
                Map.of(
                        MessageTypeEnums.EMAIL, TaskNotifyEnums.COMPLETED.getCode(),
                        MessageTypeEnums.SMS, TaskNotifyEnums.COMPLETED.getCode(),
                        MessageTypeEnums.SITE, TaskNotifyEnums.COMPLETED.getCode()
                ),
                message
        );

    }
}
