package com.deschen.security.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author deschen
 * @Create 2019/8/17
 * @Description
 * @Since 1.0.0
 */
@Data
public class ImageCode {

    private BufferedImage image;

    private String code;  //验证码

    private LocalDateTime expireTime;  // 过期时间

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        this.image = image;
        this.code = code;
        this.expireTime = expireTime;
    }

    public ImageCode(BufferedImage image, String code, Long expireIn) {
        this.image = image;
        this.code = code;
        // 什么时候过期
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
