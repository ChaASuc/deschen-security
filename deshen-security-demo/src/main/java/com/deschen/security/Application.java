package com.deschen.security;

import com.deschen.security.core.vo.ResultVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author deschen
 * @Create 2019/8/11
 * @Description
 * @Since 1.0.0
 */
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/demo")
    public String getDemo() {
        return "demo";
    }

    @GetMapping("/me")
    public ResultVO getUserDetail(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResultVO().success(userDetails);
    }
}
