package com.hb0730.zoom.base.sys.system.mapper;

import com.hb0730.zoom.base.sys.system.entity.SysUserSubscribeMsg;
import com.hb0730.zoom.mybatis.core.mapper.IMapper;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/1/2
 */
@Repository
public interface UserSubscribeMsgMapper extends IMapper<SysUserSubscribeMsg> {
    /**
     * 查询用户订阅
     *
     * @param userId 用户ID
     * @param code   代码
     * @return 用户订阅
     */
    SysUserSubscribeMsg findUserSubscribe(String userId, String code);
}
