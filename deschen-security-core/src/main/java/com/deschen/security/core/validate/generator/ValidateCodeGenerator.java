package com.deschen.security.core.validate.generator;

import com.deschen.security.core.validate.code.ImageCode;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author deschen
 * @Create 2019/8/18
 * @Description 验证码生成器接口
 * @Since 1.0.0
 */
public interface ValidateCodeGenerator {

    ImageCode createImageCode(HttpServletRequest request);
}
