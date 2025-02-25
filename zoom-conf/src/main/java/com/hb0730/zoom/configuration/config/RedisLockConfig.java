package com.hb0730.zoom.configuration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redis锁配置
 *
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/2/25
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zoom.redis.lock")
public class RedisLockConfig {

    /**
     * 锁的前缀
     */
    private String prefix = "zoom:lock";
    /**
     * 锁的过期时间
     */
    private long expire = 60000L;
}
