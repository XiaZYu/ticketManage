package com.example.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "auth.jwt")
public class AuthJwtProperties {

    //JWT 密钥
    private String secret;

    //accessToken 有效时间
    private Long expiration;

    //header名称
    private String header;

    //黑名单
    private List<String> includePaths;

    //白名单
    private List<String> excludePaths;

}
