package com.hb0730.zoom.quartz.event;

import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/29
 */
@Getter
public class CompletedEvent extends ApplicationEvent {
    private final TaskInfo taskInfo;

    public CompletedEvent(TaskInfo taskInfo, Object source) {
        super(source);
        this.taskInfo = taskInfo;
    }
}
