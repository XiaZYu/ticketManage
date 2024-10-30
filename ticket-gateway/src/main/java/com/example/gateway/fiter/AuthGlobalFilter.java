package com.example.gateway.fiter;

import com.alibaba.fastjson2.JSONObject;
import com.example.gateway.config.AuthJwtProperties;
import com.example.gateway.utils.JwtUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private final JwtUtils jwtUtils;
    private final AuthJwtProperties authJwtProperties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取request
            ServerHttpRequest request = exchange.getRequest();
            //判断是否需要登录拦截
            if (isExclude(request.getPath().toString())){
                System.out.println(request.getPath().toString() + "不需要登录拦截");
                //放行
                return chain.filter(exchange);
            }
            //获取token
            String token = null;
            Claims user = null;
            List<String> headers = request.getHeaders().get("Authorization");
            if (headers != null && !headers.isEmpty()) {
                token = headers.get(0);
            }
            //解析token
            try {
                user = JwtUtils.parseJWT(authJwtProperties.getSecret(), token);
        }catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //传递用户信息
        String userInfo = user.get("uid").toString();

        ServerWebExchange ex = exchange.mutate()
                .request(builder -> builder.header("userInfo", userInfo))
                .build();
        //放行
        return chain.filter(ex);
    }

    private boolean isExclude(String string) {
        for (String excludePath : authJwtProperties.getExcludePaths()) {
            if (antPathMatcher.match(excludePath, string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
