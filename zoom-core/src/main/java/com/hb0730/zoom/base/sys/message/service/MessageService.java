package com.hb0730.zoom.base.sys.message.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.enums.MessageContentTypeEnums;
import com.hb0730.zoom.base.enums.MessageSendStatusEnums;
import com.hb0730.zoom.base.enums.MessageTypeEnums;
import com.hb0730.zoom.base.ext.services.dto.SaveMessageDTO;
import com.hb0730.zoom.base.ext.services.dto.SendMessageDTO;
import com.hb0730.zoom.base.sys.message.entity.SysMessage;
import com.hb0730.zoom.base.sys.message.entity.SysMessageTemplate;
import com.hb0730.zoom.base.sys.message.handle.ISendMsgHandle;
import com.hb0730.zoom.base.sys.message.handle.SendMsgHandleFactory;
import com.hb0730.zoom.base.sys.message.mapper.MessageMapper;
import com.hb0730.zoom.base.utils.CollectionUtil;
import com.hb0730.zoom.base.utils.MapUtil;
import com.hb0730.zoom.base.utils.StrUtil;
import com.hb0730.zoom.mybatis.core.encrypt.MybatisEncryptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消息服务
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService extends ServiceImpl<MessageMapper, SysMessage> {
    private final MessageTemplateService messageTemplateService;
    private final MybatisEncryptService mybatisEncryptService;
    private final SendMsgHandleFactory sendMsgHandleFactory;


    /**
     * 读取消息中心数据，只查询未发送的和发送失败不超过次数的
     *
     * @param params 参数
     * @return 消息
     */
    public List<SysMessage> getPendingMessages(Map<String, String> params) {
        // 查询未发送的和发送失败不超过次数的
        Wrapper<SysMessage> wrapper = Wrappers.lambdaQuery(SysMessage.class)
                .eq(SysMessage::getMsgSendStatus, MessageSendStatusEnums.WAITING.getCode())
                .or()
                .lt(SysMessage::getMsgSendNum, 6)
                .eq(SysMessage::getMsgSendStatus, MessageSendStatusEnums.FAILURE.getCode());
        return list(wrapper);
    }

    /**
     * 发送消息
     *
     * @param messages 消息
     * @return 是否成功
     */
    public R<?> doSendMessage(List<SysMessage> messages) {
        if (CollectionUtil.isEmpty(messages)) {
            return R.OK("没有需要发送的消息");
        }
        for (SysMessage message : messages) {
            Integer sendNum = message.getMsgSendNum();
            try {
                // 发送消息
                MessageTypeEnums msgType = MessageTypeEnums.of(message.getMsgType());
                if (null == msgType) {
                    log.error("发送消息失败,消息类型不存在: {}", message.getMsgType());
                    continue;
                }

                ISendMsgHandle sendMsgHandle = sendMsgHandleFactory.get(msgType);
                if (null == sendMsgHandle) {
                    log.error("发送消息失败,消息类型不存在: {}", message.getMsgType());
                    continue;
                }
                // 解密消息内容
                String receiver = mybatisEncryptService.decrypt(message.getMsgReceiver());
                message.setMsgReceiver(receiver);
                String content = mybatisEncryptService.decrypt(message.getMsgContent());
                message.setMsgContent(content);
                String contentHtml = mybatisEncryptService.decrypt(message.getMsgContentHtml());
                message.setMsgContentHtml(contentHtml);
                String param = mybatisEncryptService.decrypt(message.getMsgParam());
                message.setMsgParam(param);

                SendMessageDTO sendMessageDTO = new SendMessageDTO();
                // 消息接收人
                sendMessageDTO.setReceiver(message.getMsgReceiver());
                // 消息标题
                sendMessageDTO.setTitle(message.getMsgTitle());

                String _content = getContent(message);
                // 消息内容
                sendMessageDTO.setContent(_content);
                // 内容类型
                sendMessageDTO.setContentType(msgType.getContentType());

                sendMsgHandle.sendMsg(sendMessageDTO);
//                sendMsgHandle.sendMsg(message.getMsgReceiver(), message.getMsgTitle(), message.getMsgContent());
                // 更新消息状态
                message.setMsgSendStatus(MessageSendStatusEnums.SUCCESS.getCode());
                message.setMsgSendTime(new Date());
            } catch (Exception e) {
                log.error("发送消息失败", e);
                // 更新消息状态
                message.setMsgSendStatus(MessageSendStatusEnums.FAILURE.getCode());
                message.setMsgResult(e.getMessage().substring(0, 200));
            }
            message.setMsgSendNum(sendNum + 1);
            updateById(message);
        }
        return R.OK("发送消息成功");
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
        message.setMsgSendStatus(MessageSendStatusEnums.WAITING.getCode());
        // 设置消息类型
        message.setMsgType(dto.getMsgType());
        // 接收者
        message.setMsgReceiver(dto.getReceiver());
        // 发送次数
        message.setMsgSendNum(0);
        // 编辑消息内容
        if (CollectionUtil.isEmpty(extra)) {
            extra = MapUtil.newHashMap();
        }
        message.setMsgContent(StringSubstitutor.replace(messageTemplate.getTemplateContentText(), extra));
        message.setMsgContentHtml(StringSubstitutor.replace(messageTemplate.getTemplateContentHtml(), extra));
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

    private String getContent(SysMessage message) {
        String msgType = message.getMsgType();
        MessageTypeEnums messageTypeEnums = MessageTypeEnums.of(msgType);
        if (null == messageTypeEnums) {
            return null;
        }
        MessageContentTypeEnums contentType = messageTypeEnums.getContentType();
        return switch (contentType) {
            case TEXT -> message.getMsgContent();
            case HTML -> message.getMsgContentHtml();
            default -> null;
        };

    }
}
