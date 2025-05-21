package com.hb0730.zoom.sys.biz.message.service;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.core.service.BaseService;
import com.hb0730.zoom.base.sys.message.entity.SysMessage;
import com.hb0730.zoom.mybatis.core.encrypt.MybatisEncryptService;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageCreateRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageQueryRequest;
import com.hb0730.zoom.sys.biz.message.model.request.SysMessageUpdateRequest;
import com.hb0730.zoom.sys.biz.message.model.vo.SysMessageVO;
import com.hb0730.zoom.sys.biz.message.repository.SysMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SysMessageService extends BaseService<String, SysMessageQueryRequest, SysMessageVO, SysMessage,
        SysMessageCreateRequest, SysMessageUpdateRequest, SysMessageRepository> {
    private final MybatisEncryptService mybatisEncryptService;


    /**
     * 解密
     *
     * @param content 内容
     * @return 解密后的内容
     */
    public R<String> decrypt(String content) {
        return R.OK(mybatisEncryptService.decrypt(content));
    }

//    @Override
//    public Wrapper<SysMessage> getQueryWrapper(SysMessageQueryRequest query) {
//        String msgReceiver = query.getMsgReceiver();
//        if (StrUtil.isNotBlank(msgReceiver)) {
//            query.setMsgReceiver(mybatisEncryptService.encrypt(msgReceiver));
//        }
//        return super.getQueryWrapper(query);
//    }
}
