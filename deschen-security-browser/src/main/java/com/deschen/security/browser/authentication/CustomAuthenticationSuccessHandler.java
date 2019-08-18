package com.deschen.security.browser.authentication;

import com.deschen.security.core.enums.LoginTypeEnum;
import com.deschen.security.core.properties.SecurityProperties;
import com.deschen.security.core.vo.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author deschen
 * @Create 2019/8/16
 * @Description 自定义认证成功处理器
 * @Since 1.0.0
 */
//SavedRequestAwareAuthenticationSuccessHandler security默认成功处理器
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ObjectMapper objectMapper;

    // 对认证成功处理，异步响应json字段，或同步返回原先页面
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("【认证处理】登入成功");
        // 判断是json还是html
        LoginTypeEnum loginTypeEnum = securityProperties.getBrowser().getLoginType();
        if (LoginTypeEnum.JSON.equals(loginTypeEnum)) {
            // json返回
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(
                    objectMapper.writeValueAsString(
                            new ResultVO().success(authentication)));
        } else {
            // html跳转，默认跳转原先的请求
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
