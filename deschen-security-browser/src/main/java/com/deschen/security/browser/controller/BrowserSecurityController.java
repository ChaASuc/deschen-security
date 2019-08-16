package com.deschen.security.browser.controller;

import com.deschen.security.core.properties.SecurityProperties;
import com.deschen.security.core.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author deschen
 * @Create 2019/8/15
 * @Description 浏览器权限控制层
 * @Since 1.0.0
 */
@RestController
@Slf4j
public class BrowserSecurityController {

    // 缓存请求
    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;
    /**
     *
     * @param request
     * @param response
     * @return
     * @description: 身份认证的处理，html请求转到登入页，json请求报错
     */
    @RequestMapping("/authentication/require")
    public ResultVO requireAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 从Session缓存中获取用户的请求
        // 因为Spring Security Web认证机制(通常指表单登录)中登录成功后页面需要跳转到原来客户请求的URL。
        // 该过程中首先需要将原来的客户请求缓存下来，然后登录成功后将缓存的请求从缓存中提取出来。
        // 针对该需求，Spring Security Web 提供了在http session中缓存请求的能力，也就是HttpSessionRequestCache。
        // HttpSessionRequestCache所保存的请求必须封装成一个SavedRequest接口对象，实际上，HttpSessionRequestCache
        // 总是使用自己的SavedRequest缺省实现DefaultSavedRequest。
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();   // 引发跳转的请求
            log.info("【身份认证的处理】 用户发送的请求, url = {}", redirectUrl);
            if (StringUtils.endsWithIgnoreCase(redirectUrl, ".html")) {
                // 如果是带有.html请求，则跳转到配置文件设置的登入页面
                redirectStrategy.sendRedirect(request, response,
                        securityProperties.getBrowserPorperties().getLoginPage());
            }
            // 如果是json请求，则报错401,未授权错误
            return new ResultVO().setCode(HttpStatus.UNAUTHORIZED.value())
                    .setMsg(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return null;
    }
}
