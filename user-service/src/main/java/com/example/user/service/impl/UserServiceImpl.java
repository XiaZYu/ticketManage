package com.example.user.service.impl;


import com.example.api.po.Result;
import com.example.user.domain.po.User;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public int countUser() {
        return userMapper.countUser();
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public List<User> findUser(Long phone, String nickname, String name, int pageNumber, int pageSize) {
        int size =( pageNumber - 1) * pageSize;
        return userMapper.findUser(phone, nickname, name, size, pageSize);
    }

    @Override
    public User findUserByPhone(Long phone) {
        return userMapper.findUserByPhone(phone);
    }

    @Override
    public User findUserByNickname(String nickname) {
        return userMapper.findUserByNickname(nickname);
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    public User findUserInfoByUid(String uid) {
        return userMapper.findUserInfoByUid(uid);
    }

    @Override
    public List<User> getUserList(int pageNumber, int pageSize) {
        int size =( pageNumber - 1) * pageSize;
        return userMapper.getUserList(size, pageSize);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(String uid) {
        userMapper.deleteUser(uid);
    }

    @Override
    public User findUserById(String id) {
        return userMapper.findUserById(id);
    }


}
