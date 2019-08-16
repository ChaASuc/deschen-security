package com.deschen.security.browser.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author deschen
 * @Create 2019/8/15
 * @Description 自定义用户认证服务
 * @Since 1.0.0
 */
@Component
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @description 根据用户名，获取用户信息，封装返回 User（即UserDetails实现类）对象
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("【CustomUserDetailService】根据用户名获取用户信息, username = {}", username);
        String password = passwordEncoder.encode("123");
        User user = new User("zhangsan",  /// 用户名
                password,  // 密码
                true,  // 账号是否失效
                true,   // 账号是否过期
                true,  // 密码是否过期
                true,  // 账号是否冻结
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        log.info("【CustomUserDetailService】 password = {}", password);
        log.info("【CustomUserDetailService】获取User对象，user = {}", user);
        return user;
    }
}
