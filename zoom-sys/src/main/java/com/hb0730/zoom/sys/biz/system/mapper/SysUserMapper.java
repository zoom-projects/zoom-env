package com.hb0730.zoom.sys.biz.system.mapper;

import com.hb0730.zoom.base.sys.system.entity.SysUser;
import com.hb0730.zoom.mybatis.core.mapper.IMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/9/24
 */
@Repository
public interface SysUserMapper extends IMapper<SysUser> {
    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    @Select("select * from sys_user where username = #{username} limit 1")
    SysUser findByUsername(String username);


    /**
     * 根据手机号查询
     *
     * @param phone 手机号
     * @return 用户
     */
    @Select("select * from sys_user where phone = #{phone} limit 1")
    SysUser findByPhone(String phone);
}
