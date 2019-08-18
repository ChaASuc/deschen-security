package com.deschen.security.core.validate.controller;

import com.deschen.security.core.properties.ImageCodeProperties;
import com.deschen.security.core.properties.SecurityProperties;
import com.deschen.security.core.validate.code.ImageCode;
import com.deschen.security.core.validate.generator.ValidateCodeGenerator;
import org.bouncycastle.crypto.tls.SecurityParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Security;
import java.util.Random;

/**
 * @Author deschen
 * @Create 2019/8/17
 * @Description 校验控制层
 * @Since 1.0.0
 */
@RestController
public class ValidateCodeController {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @Autowired
    private ValidateCodeGenerator validateCodeGenerator;

    /**
     * 获取验证码图片
     *
     * @param request
     * @param response
     */
    @GetMapping("/code/image")
    public void getCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建验证码图片
        ImageCode imageCode = validateCodeGenerator.createImageCode(request);
        // 存在session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        // response输出流图片
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

}
