package com.hb0730.zoom.sys.biz.system.repository.mapper;

import com.hb0730.zoom.base.sys.system.entity.SysUserRole;
import com.hb0730.zoom.mybatis.core.mapper.IMapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/15
 */
@Repository
public interface SysUserRoleMapper extends IMapper<SysUserRole> {

    @Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteByUserId(String userId);
}
