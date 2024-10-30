package com.example.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    //JWT 密钥
    private String secret;

    //accessToken 有效时间
    private Long expiration;

}
