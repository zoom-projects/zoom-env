package com.hb0730.zoom.sys.biz.system.service;

import com.hb0730.zoom.base.core.service.BaseService;
import com.hb0730.zoom.base.sys.system.entity.SysAttachment;
import com.hb0730.zoom.sys.biz.system.model.request.attachment.SysAttachmentCreateRequest;
import com.hb0730.zoom.sys.biz.system.model.request.attachment.SysAttachmentQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysAttachmentVO;
import com.hb0730.zoom.sys.biz.system.repository.SysAttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 附件
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/20
 */
@Service
@Slf4j
public class SysAttachmentService extends BaseService<String, SysAttachmentQueryRequest, SysAttachmentVO,
        SysAttachment, SysAttachmentCreateRequest, SysAttachmentCreateRequest, SysAttachmentRepository> {
}
