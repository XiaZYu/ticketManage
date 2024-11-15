package com.example.user.domain.vo;

import lombok.Data;

@Data
public class ChangePsd {
    private String oldPassword;

    private String newPassword;

    private String repeatPassword;
}
