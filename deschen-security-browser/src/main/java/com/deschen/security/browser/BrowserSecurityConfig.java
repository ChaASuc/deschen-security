package com.deschen.security.browser;

import com.deschen.security.browser.authentication.CustomAuthenticationFailtureHandler;
import com.deschen.security.browser.authentication.CustomAuthenticationSuccessHandler;
import com.deschen.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author deschen
 * @Create 2019/8/14
 * @Description 浏览器安全配置
 * @Since 1.0.0
 */
@Configuration
@Slf4j
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailtureHandler authenticationFailtureHandler;
    /**
     * 加密解密接口
     * 方法：
     * 1. String encode(CharSequence rawPassword)随机生成盐进行加密，-》
     * 优点：同样密码，随机生成不同盐加密后都不一样，避免被人破解密码
     * 2. boolean matches(CharSequence rawPassword, String encodedPassword); -》
     * 浏览器登入密码与存在数据库的密码比较，为true就通过
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * 用来配置用户签名服务，主要是user-details 机制，你还可以给予用户赋予角色
     * @param auth 签名管理器构造器， 用于构建用户具体权限控制
     *  定义用户（ user ）、密码（ password ）和角色（ role ），在默认的情况下
     *  登入用户：user； 密码：控制器显示一大串字符
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    /**
     *  用来配置Filter 链
     *  @param web 主要是配置Filter链的内容，可以配置Filter链忽略哪些内容。
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * 用来配置拦截保护的请求，比如什么请求放行，什么请求需要验证
     *
     * @param http 安全请求对象
     *             指定用户和角色与对应 URL 的访问权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin() // 登入页面
                .loginPage("/authentication/require")  //登入页面请求
                // 用于提交登入信息，被UsernamePasswordAuthenticationFilter认证
                .loginProcessingUrl("/authentication/form")
                // 认证成功处理
                .successHandler(authenticationSuccessHandler)
                // 认证失败处理
                .failureHandler(authenticationFailtureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowserPorperties().getLoginPage(),
                        "/error"    // springboot默认错误链接，避免被"/authentication/require"拦截
                ).permitAll()   // 登入页面请求无条件允许通过
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();  //停用csrf功能
    }




}
