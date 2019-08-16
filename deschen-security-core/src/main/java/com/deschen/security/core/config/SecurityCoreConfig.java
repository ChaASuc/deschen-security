package com.deschen.security.core.config;

import com.deschen.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author deschen
 * @Create 2019/8/16
 * @Description Security核心配置
 * @Since 1.0.0
 */
@EnableConfigurationProperties(SecurityProperties.class)
@Configuration
public class SecurityCoreConfig {
}
