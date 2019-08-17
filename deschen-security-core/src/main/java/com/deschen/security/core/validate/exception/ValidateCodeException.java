package com.deschen.security.core.validate.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Author deschen
 * @Create 2019/8/17
 * @Description
 * @Since 1.0.0
 */
// AuthenticationException security所有身份认证的异常基类
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String explanation) {
        super(explanation);
    }
}
