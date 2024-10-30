package com.example.trade;

import com.example.api.client.FilmClient;
import com.example.api.client.SessionClient;
import com.example.api.client.UserClient;
import com.example.api.config.DefaultFeignConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(clients = {UserClient.class, SessionClient.class, FilmClient.class},defaultConfiguration = DefaultFeignConfig.class)
@MapperScan("com.example.trade.mapper")
public class TradeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }
}