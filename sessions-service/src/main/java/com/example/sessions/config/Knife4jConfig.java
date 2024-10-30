package com.example.sessions.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("电影票务系统接口文档")
                        .version("1.0")
                        .description("场次管理服务接口文档")
                        .termsOfService("localhost:8083/session")
                );
    }
}