package com.example.user.domain.po;
/*
* 用户实体类
* */

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String uid;

    private String nickname;

    private String name;

    private String password;

    private String gender;

    private Integer age;

    private Long phone;

    private String email;

    private String role;

    private double balance;
}
