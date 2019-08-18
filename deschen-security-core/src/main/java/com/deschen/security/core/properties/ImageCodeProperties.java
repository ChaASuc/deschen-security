package com.deschen.security.core.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author deschen
 * @Create 2019/8/18
 * @Description 验证码中的图片验证码配置类
 * @Since 1.0.0
 */
@Data
public class ImageCodeProperties {

    private int width = 60;

    private int height = 20;

    private int length = 4;

    @Value("#{'${url}'.split(',')}")
    private String[] urls = {"/authentication/form"};
}
