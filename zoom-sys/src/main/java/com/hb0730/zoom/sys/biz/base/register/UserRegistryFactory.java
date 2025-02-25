package com.hb0730.zoom.sys.biz.base.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:huangbing0730@gmail">hb0730</a>
 * @date 2025/2/25
 */
@Component
@Slf4j
public class UserRegistryFactory {
    private final Map<String, UserRegistry> registryMap = new ConcurrentHashMap<>();


    public UserRegistryFactory(ApplicationContext applicationContext) {
        Map<String, UserRegistry> beansOfType = applicationContext.getBeansOfType(UserRegistry.class);
        beansOfType.forEach((k, v) -> {
            RegistryType type = v.getType();
            registryMap.put(type.name().toLowerCase(), v);
        });
    }

    /**
     * 获取注册
     *
     * @param type 类型
     * @return 注册
     */
    public UserRegistry getRegistry(RegistryType type) {
        return registryMap.get(type.name().toLowerCase());
    }
}
