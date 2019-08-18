package com.deschen.security.core.validate.config;

import com.deschen.security.core.properties.SecurityProperties;
import com.deschen.security.core.validate.generator.ImageCodeGenerator;
import com.deschen.security.core.validate.generator.ValidateCodeGenerator;
import com.deschen.security.core.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author deschen
 * @Create 2019/8/18
 * @Description 验证码配置类
 * @Since 1.0.0
 */
@Configuration
public class ValidateCodeBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Bean
    @ConditionalOnMissingBean(name = "validateCodeGenerator")
    public ValidateCodeGenerator validateCodeGenerator() {
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

}
