package com.hb0730.zoom.sys.biz.system.model.request.user;

import com.hb0730.zoom.mybatis.query.annotation.Equals;
import com.hb0730.zoom.mybatis.query.doamin.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/3
 */
@Schema(description = "用户访问令牌查询请求")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserAccessTokenQueryRequest extends PageRequest {
    /**
     * 用户ID
     */
    @Equals
    @Schema(description = "用户ID")
    private String userId;


    public static SysUserAccessTokenQueryRequest of(PageRequest request) {
        SysUserAccessTokenQueryRequest queryRequest = new SysUserAccessTokenQueryRequest();
        queryRequest.setCurrent(request.getCurrent());
        queryRequest.setSize(request.getSize());
        queryRequest.setSorts(request.getSortsStr());
        return queryRequest;
    }
}
