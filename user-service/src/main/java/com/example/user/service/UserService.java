package com.example.user.service;

import com.example.api.po.Result;
import com.example.user.domain.po.User;

import java.util.List;

public interface UserService{
    int countUser(); // 获取用户总数

    int addUser(User user); // 注册

    List<User> findUser(Long phone, String nickname, String name, int pageNumber, int pageSize); // 根据用户名、昵称、真实姓名查询用户

    User findUserByPhone(Long phone); // 根据用户手机号查询用户信息

    User findUserByNickname(String nickname); // 根据用户昵称查询用户信息

    User findUserByName(String name); // 根据用户真实姓名查询用户信息

    User findUserInfoByUid(String uid); // 根据用户id查询用户信息

    List<User> getUserList(int pageNumber, int pageSize); // 获取用户列表

    int updateUser(User user); // 更新用户信息

    void deleteUser(String uid); // 删除或注销用户

    User findUserById(String id); // 根据用户id查询用户信息
}
