package com.example.api.po;
/*
* 用户实体类
* */

import lombok.Data;

@Data
public class User {

    private String uid;

    private String nickname;

    private String name;

    private String password;

    private String gender;

    private Integer age;

    private Long phone;

    private String email;

    private String role;
}
