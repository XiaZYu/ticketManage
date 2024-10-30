package com.example.user.domain.vo;

import com.example.user.domain.po.User;
import lombok.Data;

import java.util.List;

@Data
public class UserList {
    private Integer count;
    private Integer page;
    private Integer size;
    private List<User> list;
}
