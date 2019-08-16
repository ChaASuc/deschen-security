package com.deschen.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author deschen
 * @Create 2019/8/16
 * @Description Security配置文件类
 * @Since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "deschen.security")
public class SecurityProperties {

    private BrowserProperties browserPorperties = new BrowserProperties();
}

