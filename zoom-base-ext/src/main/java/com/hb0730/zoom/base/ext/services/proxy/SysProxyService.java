package com.hb0730.zoom.base.ext.services.proxy;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Option;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.enums.TaskCategoryEnums;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.ext.services.dto.SaveOperatorLogDTO;
import com.hb0730.zoom.base.ext.services.dto.UserDTO;
import com.hb0730.zoom.base.ext.services.dto.UserInfoDTO;
import com.hb0730.zoom.base.ext.services.dto.task.BizTaskCreateDTO;
import com.hb0730.zoom.base.ext.services.dto.task.TaskInfo;
import com.hb0730.zoom.base.ext.services.remote.SysBizTaskRpcService;
import com.hb0730.zoom.base.ext.services.remote.SysDictRpcService;
import com.hb0730.zoom.base.ext.services.remote.SysNotifyRpcService;
import com.hb0730.zoom.base.ext.services.remote.SysOperatorLogRpcService;
import com.hb0730.zoom.base.ext.services.remote.SysUserRpcService;
import com.hb0730.zoom.base.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统消息发送RPC代理
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Service
@Slf4j
public class SysProxyService implements SysUserRpcService, SysNotifyRpcService, SysOperatorLogRpcService,
        SysDictRpcService, SysBizTaskRpcService {

    @Autowired
    private SysNotifyRpcService notifyRpcService;
    @Autowired
    private SysUserRpcService userRpcService;
    @Autowired
    private SysOperatorLogRpcService operatorLogRpcService;

    @Autowired
    private SysDictRpcService dictRpcService;

    @Autowired
    private SysBizTaskRpcService sysBizTaskRpcService;


    @Override
    public R<?> doSendMessages(Map<String, String> params) {
        return notifyRpcService.doSendMessages(params);
    }

    @Override
    public R<?> saveMessage(SaveMessageDTO dto) {
        return notifyRpcService.saveMessage(dto);
    }

    @Override
    public UserInfoDTO findUsername(String username) {
        return userRpcService.findUsername(username);
    }

    @Override
    public boolean checkOpenApiAuth(String token, String apiName) {
        return userRpcService.checkOpenApiAuth(token, apiName);
    }

    @Override
    public UserDTO findUserByToken(String token) {
        return userRpcService.findUserByToken(token);
    }

    @Override
    public void saveOperatorLog(SaveOperatorLogDTO dto) {
        operatorLogRpcService.saveOperatorLog(dto);
    }


    @Override
    public List<Object> getDictItems(String dictCode) {
        return dictRpcService.getDictItems(dictCode);
    }

    @Override
    public List<Option> loadItems(String dictCode) {
        return dictRpcService.loadItems(dictCode);
    }

    @Override
    public R<String> saveTask(BizTaskCreateDTO dto) {
        return sysBizTaskRpcService.saveTask(dto);
    }

    @Override
    public TaskInfo getWorkingTask(TaskCategoryEnums taskCategory, String appName) {
        return sysBizTaskRpcService.getWorkingTask(taskCategory, appName);
    }

    @Override
    public TaskInfo getPengdingTask(TaskCategoryEnums taskCategory, String appName) {
        return sysBizTaskRpcService.getPengdingTask(taskCategory, appName);
    }

    @Override
    public Boolean setTaskStartState(String taskId, Integer taskState, String taskStartTime) {
        return sysBizTaskRpcService.setTaskStartState(taskId, taskState, taskStartTime);
    }

    @Override
    public void updateTastImmediately(TaskInfo taskInfo) {
        sysBizTaskRpcService.updateTastImmediately(taskInfo);
    }

    @Override
    public boolean subscribedMsg(String username, String code, MessageTypeEnums msgType) {
        return userRpcService.subscribedMsg(username, code, msgType);
    }

    @Override
    public R<?> saveMessageAllNotice(String username,
                                     String subscribeCode,
                                     Map<MessageTypeEnums, String> msgTemplateCode,
                                     SaveMessageDTO dto) {
        UserInfoDTO userInfo = findUsername(username);
        if (userInfo == null) {
            return R.NG("用户不存在");
        }
        boolean subscribedMsg = subscribedMsg(username, subscribeCode, MessageTypeEnums.EMAIL);
        if (subscribedMsg && StrUtil.isNotBlank(userInfo.getEmail())) {
            log.info("任务完成，发送 Email 消息通知");
            dto.setReceiver(userInfo.getEmail());
            dto.setMsgType(MessageTypeEnums.EMAIL.getCode());
            String code = msgTemplateCode.get(MessageTypeEnums.EMAIL);
            dto.setTemplateCode(code);
            return saveMessage(dto);
        }
        subscribedMsg = subscribedMsg(username, subscribeCode, MessageTypeEnums.SMS);
        if (subscribedMsg && StrUtil.isNotBlank(userInfo.getPhone())) {
            log.info("任务完成，发送 SMS 消息通知");
            dto.setReceiver(userInfo.getPhone());
            dto.setMsgType(MessageTypeEnums.SMS.getCode());
            String code = msgTemplateCode.get(MessageTypeEnums.SMS);
            dto.setTemplateCode(code);
            return saveMessage(dto);
        }

        subscribedMsg = subscribedMsg(username, subscribeCode, MessageTypeEnums.SITE);
        if (subscribedMsg) {
            log.info("任务完成，发送站内消息通知");
            dto.setReceiver(userInfo.getUsername());
            dto.setMsgType(MessageTypeEnums.SITE.getCode());
            String code = msgTemplateCode.get(MessageTypeEnums.SITE);
            dto.setTemplateCode(code);
            return saveMessage(dto);
        }
        return R.OK();
    }
}
