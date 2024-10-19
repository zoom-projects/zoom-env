package com.hb0730.zoom.quartz.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2024/10/17
 */
@Configuration
@Slf4j
public class SchedulerConfig implements SchedulerFactoryBeanCustomizer {
    @Autowired
    private DataSource dataSource;

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        // 设置数据源
        schedulerFactoryBean.setDataSource(determineQuartzDataSource());

        // 设置其他属性
    }

    /**
     * @return quartz专用数据源
     */
    private DataSource determineQuartzDataSource() {
        Map<String, DataSource> ds = ((DynamicRoutingDataSource) dataSource).getDataSources();
        if (ds.containsKey("quartz")) {
            return ds.get("quartz");
        }
        return dataSource;
    }
}
