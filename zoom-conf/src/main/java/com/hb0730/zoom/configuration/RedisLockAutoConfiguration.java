package com.hb0730.zoom.configuration;

import com.hb0730.zoom.configuration.config.RedisLockConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/2/25
 */
@EnableConfigurationProperties(RedisLockConfig.class)
@AutoConfiguration
public class RedisLockAutoConfiguration {

    /**
     * 注入RedisLockRegistry
     *
     * @param redisConnectionFactory redis连接工厂
     * @param redisLockConfig        redis锁配置
     * @return RedisLockRegistry
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory,
                                               RedisLockConfig redisLockConfig) {
        return new RedisLockRegistry(redisConnectionFactory, redisLockConfig.getPrefix(),
                redisLockConfig.getExpire());
    }
}
