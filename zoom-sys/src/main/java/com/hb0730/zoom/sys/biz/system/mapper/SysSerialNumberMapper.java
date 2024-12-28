package com.hb0730.zoom.sys.biz.system.mapper;

import com.hb0730.zoom.mybatis.core.mapper.IMapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 序列号
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/12/27
 */
@Repository
public interface SysSerialNumberMapper extends IMapper<String> {

    /**
     * 获取任务单号
     *
     * @param params
     * @return
     */
    @Select({"CALL proc_serialnumber(#{p_CATEGORY, mode=IN, javaType=String,jdbcType=VARCHAR},#{p_PREFIX, mode=IN, javaType=String,jdbcType=VARCHAR}," +
            "#{p_OUT_SN, mode=OUT, javaType=String, jdbcType=VARCHAR})"})
    @Options(statementType = StatementType.CALLABLE)
    String getSerialnumber(Map<String, String> params);

}
