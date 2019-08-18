package com.deschen.security.core.properties;

import com.deschen.security.core.enums.LoginTypeEnum;
import lombok.Data;

/**
 * @Author deschen
 * @Create 2019/8/16
 * @Description 浏览器配置类
 * @Since 1.0.0
 */
@Data
public class BrowserProperties {

    // 如果demo模块没有配置其值，那么默认用定好的
    private String loginPage = "/default-login.html";

    private LoginTypeEnum loginType = LoginTypeEnum.JSON;

}
