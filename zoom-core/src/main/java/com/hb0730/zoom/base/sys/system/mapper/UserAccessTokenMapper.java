package com.hb0730.zoom.base.sys.system.mapper;

import com.hb0730.zoom.base.sys.system.entity.SysUserAccessToken;
import com.hb0730.zoom.mybatis.core.mapper.IMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/11/4
 */
@Repository
public interface UserAccessTokenMapper extends IMapper<SysUserAccessToken> {

    /**
     * 根据token获取openApi
     *
     * @param token token
     * @return openApi
     */
    @Select("select open_api_id from sys_user_access_token_open_api where access_token = #{token} ")
    List<String> getOpenApisByToken(String token);

    /**
     * 根据token获取用户token
     *
     * @param token token
     * @return 用户token
     */
    @Select("select * from sys_user_access_token where access_token = #{token} and status = 1 limit 1")
    SysUserAccessToken findByToken(String token);
}
