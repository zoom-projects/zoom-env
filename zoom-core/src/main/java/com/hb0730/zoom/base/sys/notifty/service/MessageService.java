package com.hb0730.zoom.base.sys.notifty.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.service.dto.SaveMessageDTO;
import com.hb0730.zoom.base.sys.notifty.entity.SysMessage;
import com.hb0730.zoom.base.sys.notifty.entity.SysMessageTemplate;
import com.hb0730.zoom.base.sys.notifty.mapper.MessageMapper;
import com.hb0730.zoom.base.utils.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService extends ServiceImpl<MessageMapper, SysMessage> {
    private final MessageTemplateService messageTemplateService;


    /**
     * 发送消息
     *
     * @param messages 消息
     * @return 是否成功
     */
    public R<?> doSendMessage(List<SysMessage> messages) {

        return R.NG("发送消息失败,暂未实现");
    }

    /**
     * 保存消息
     *
     * @param dto 消息
     * @return 是否成功
     */
    public R<?> saveMessage(SaveMessageDTO dto) {
        Map<String, String> extra = dto.getExtra();
        // 消息模板
        SysMessageTemplate messageTemplate = messageTemplateService.selectByCode(dto.getTemplateCode());
        if (null == messageTemplate) {
            messageTemplate = new SysMessageTemplate();
            messageTemplate.setTemplateName(extra.get("title"));      // 消息标题 或 短信签名
            messageTemplate.setTemplateContentText(extra.get("content")); // 消息内容
        }

        SysMessage message = new SysMessage();
        //消息状态
        message.setMsgSendStatus(0);
        // 设置消息类型
        message.setMsgType(dto.getMsgType());
        // 接收者
        message.setMsgReceiver(dto.getReceiver());
        // 发送次数
        message.setMsgSendNum(0);
        // 编辑消息内容
        message.setMsgContent(StringSubstitutor.replace(messageTemplate.getTemplateContentText(), extra));
        // 消息标题或短信签名
        message.setMsgTitle(dto.getTitle());
        if (StrUtil.isBlank(message.getMsgTitle())) {
            message.setMsgTitle(
                    StringSubstitutor.replace(messageTemplate.getTemplateName(), extra)
            );
        }
        // 包含短信代码的参数
        message.setMsgParam(dto.getMsgParams());

        // 保存消息
        save(message);
        return R.OK("保存消息成功");
    }
}
