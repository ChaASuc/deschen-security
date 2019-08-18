package com.deschen.security.core.properties;

import lombok.Data;

/**
 * @Author deschen
 * @Create 2019/8/18
 * @Description 验证码的配置类，用于所有验证码类型配置
 * @Since 1.0.0
 */
@Data
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();
}
