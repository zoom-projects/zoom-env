package com.hb0730.zoom.sys.biz.quartz.mapper;

import com.hb0730.zoom.mybatis.core.mapper.IMapper;
import com.hb0730.zoom.sys.biz.quartz.entity.SysQuartzJob;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/19
 */
@Repository
public interface SysQuartzJobMapper extends IMapper<SysQuartzJob> {

    /**
     * 通过id删除【物理删除】
     *
     * @param id id
     * @return 是否成功
     */
    @Delete("delete from sys_quartz_job where id = #{id}")
    boolean deleteById(String id);
}
