package com.deschen.security.validate.code;

import com.deschen.security.core.validate.code.ImageCode;
import com.deschen.security.core.validate.generator.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author deschen
 * @Create 2019/8/18
 * @Description
 * @Since 1.0.0
 */
//@Component("validateCodeGenerator")
@Slf4j
public class DemoValidateCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode createImageCode(HttpServletRequest request) {
        log.info("【自定义认证创建】");
        return null;
    }
}
