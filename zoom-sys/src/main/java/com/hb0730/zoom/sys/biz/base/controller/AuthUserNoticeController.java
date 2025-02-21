package com.hb0730.zoom.sys.biz.base.controller;

import com.hb0730.zoom.base.R;
import com.hb0730.zoom.base.data.Page;
import com.hb0730.zoom.base.ext.security.SecurityUtils;
import com.hb0730.zoom.sys.biz.base.service.AuthUserNoticeService;
import com.hb0730.zoom.sys.biz.system.model.request.message.SysNoticeMessageQueryRequest;
import com.hb0730.zoom.sys.biz.system.model.vo.SysNoticeMessageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@RequestMapping("/auth/user/notice")
@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "用户消息")
public class AuthUserNoticeController {
    private final AuthUserNoticeService authUserNoticeService;

    @GetMapping("/page")
    @Operation(summary = "查询系统消息列表")
    public R<Page<SysNoticeMessageVO>> page(SysNoticeMessageQueryRequest request) {
        SecurityUtils.getLoginUserId().ifPresent(request::setReceiverId);
        return R.OK(authUserNoticeService.page(request));
    }

    @GetMapping("/count")
    @Operation(summary = "查询系统消息数量")
    @Parameter(name = "queryUnread", description = "queryUnread", required = true)
    public R<Long> count(Boolean queryUnread) {
        Optional<String> loginUserId = SecurityUtils.getLoginUserId();
        return loginUserId.map(s -> R.OK(authUserNoticeService.count(s, queryUnread))).orElseGet(() -> R.OK(0L));
    }

    @GetMapping("/has_unread")
    @Operation(summary = "查询是否有未读消息")
    public R<Boolean> hasUnread() {
        Optional<String> loginUserId = SecurityUtils.getLoginUserId();
        return loginUserId.map(s -> R.OK(authUserNoticeService.checkHasUnreadMessage(s))).orElseGet(() -> R.OK(false));
    }


    @PutMapping("/read/{id}")
    @Operation(summary = "更新系统消息为已读")
    @Parameter(name = "id", description = "id", required = true)
    public R<String> read(@PathVariable String id) {
        SecurityUtils.getLoginUserId().ifPresent(userId -> authUserNoticeService.read(id, userId));
        return R.OK();
    }

    @PutMapping("/read_all")
    @Operation(summary = "全部标记为已读")
    public R<String> readAll() {
        SecurityUtils.getLoginUserId().ifPresent(authUserNoticeService::readAll);
        return R.OK();
    }

    /**
     * 删除消息
     *
     * @param id id
     * @return {@link R}<{@link String}>
     */
    @DeleteMapping("/del")
    @Operation(summary = "删除消息")
    @Parameter(name = "id", description = "id", required = true)
    public R<String> remove(String id) {
        SecurityUtils.getLoginUserId().ifPresent(userId -> authUserNoticeService.removeById(id, userId));
        return R.OK();
    }

    /**
     * 清空消息
     *
     * @return {@link R}
     */
    @DeleteMapping("/clear")
    @Operation(summary = "清理已读的系统消息")
    public R<?> clearMessage() {
        SecurityUtils.getLoginUserId().ifPresent(authUserNoticeService::clearSystemMessage);
        return R.OK();
    }
}
