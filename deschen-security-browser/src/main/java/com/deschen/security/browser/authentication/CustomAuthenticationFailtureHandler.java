package com.deschen.security.browser.authentication;

import com.deschen.security.core.enums.LoginTypeEnum;
import com.deschen.security.core.properties.SecurityProperties;
import com.deschen.security.core.vo.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import sun.net.httpserver.HttpsServerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author deschen
 * @Create 2019/8/16
 * @Description 自定义认证错误处理器
 * @Since 1.0.0
 */
// SimpleUrlAuthenticationFailureHandler security默认错误处理器
@Component
@Slf4j
public class CustomAuthenticationFailtureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("【认证处理】登入失败");
        // 判断json返回还是html返回
        if (LoginTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginTypeEnum())) {
            // json返回
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    objectMapper.writeValueAsString(
                            new ResultVO().failure(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    HttpStatus.INTERNAL_SERVER_ERROR.name(), exception)));
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
