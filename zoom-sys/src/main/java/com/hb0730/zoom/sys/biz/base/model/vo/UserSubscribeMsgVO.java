package com.hb0730.zoom.sys.biz.base.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户订阅消息
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/1
 */
@Data
@Schema(description = "用户订阅消息")
public class UserSubscribeMsgVO implements Serializable {
    /**
     * 模块
     */
    @JsonIgnore
    private String module;

    /**
     * 模块
     */
    @Schema(description = "模块")
    public String getName() {
        return module;
    }

    /**
     * 子集
     */
    @Schema(description = "子集")
    private List<UserSubscribeMsgChildVO> children;

    /**
     * 是否站内消息
     */
    @Schema(description = "是否站内消息")
    private Boolean isSite = false;

    /**
     * 是否email
     */
    @Schema(description = "是否email")
    private Boolean isEmail = false;
    /**
     * 是否短信
     */
    @Schema(description = "是否短信")
    private Boolean isSms = false;


    /**
     * 是否根节点
     */
    @Schema(description = "是否根节点")
    private Boolean isRoot = true;

    /**
     * 子集
     */
    @Data
    @Schema(description = "子集")
    public static class UserSubscribeMsgChildVO implements Serializable {
        @Schema(description = "主键")
        private String id;
        /**
         * 标识
         */
        @Schema(description = "标识")
        private String code;
        /**
         * 名称
         */
        @Schema(description = "名称")
        private String name;
        /**
         * 描述
         */
        @Schema(description = "描述")
        private String desc;
        /**
         * 是否站内消息
         */
        @Schema(description = "是否站内消息")
        private Boolean isSite = false;

        /**
         * 是否email
         */
        @Schema(description = "是否email")
        private Boolean isEmail = false;
        /**
         * 是否短信
         */
        @Schema(description = "是否短信")
        private Boolean isSms = false;
    }
}
