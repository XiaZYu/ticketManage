package com.example.user.domain.vo;

import lombok.Data;

@Data
public class LoginUser {
    private String token;

    private String userId;

    private String name;

    private String role;
}
