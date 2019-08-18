package com.deschen.security.core.validate.filter;

import com.deschen.security.core.properties.SecurityProperties;
import com.deschen.security.core.validate.code.ImageCode;
import com.deschen.security.core.validate.controller.ValidateCodeController;
import com.deschen.security.core.validate.exception.ValidateCodeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @Author deschen
 * @Create 2019/8/17
 * @Description 校验的验证码过滤器
 * @Since 1.0.0
 */
// OncePerRequestFilter 每次只会调用一次
//@Component
@Slf4j
@Data
public class ValidateCodeFilter extends OncePerRequestFilter {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SecurityProperties securityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("【图片验证过滤器】开始判断url，URI = {}", request.getRequestURI());
//        // 判断url是否时提交图片检验码的规定方法的请求
//        if (StringUtils.pathEquals("/authentication/form", request.getRequestURI().toString())
//                && request.getMethod().equalsIgnoreCase("post")) {
        // 重构
        String[] urls = securityProperties.getCode().getImage().getUrls();
        for (String url:
             urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                try {
                    log.info("【图片验证过滤器】开始验证图片");
                    validate(new ServletWebRequest(request));
                } catch (ValidateCodeException e) {
                    //捕获异常处理
                    log.info("【图片验证过滤器】 失败");
                    authenticationFailureHandler.onAuthenticationFailure(request, response, e);
                    return;   // 避免运行下面代码
                }
                break;
            }
        }
        filterChain.doFilter(request, response);

    }

    private void validate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
        ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(
                servletWebRequest, ValidateCodeController.SESSION_KEY);
        String codeInRequest = ServletRequestUtils.getStringParameter(
                servletWebRequest.getRequest(), "imageCode");
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (null == codeInSession) {
            throw new ValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码过期");
        }

        if (!codeInSession.getCode().equalsIgnoreCase(codeInRequest)) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY);
    }

    public static void main(String[] args) {
//        String str = StringUtils.cleanPath("http://localhost:8080/authentication/form");
//        String str2 = StringUtils.cleanPath("/authentication/form");
    }

}
