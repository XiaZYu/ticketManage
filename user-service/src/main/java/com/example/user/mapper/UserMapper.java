package com.example.user.mapper;

import com.example.api.po.Result;
import com.example.user.domain.po.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据用户昵称查询用户信息
    List<User> findUser(@Param("phone") Long phone, @Param("nickname")String nickname, @Param("name")String name, @Param("current")int current, @Param("pageSize")int pageSize);

    //分页查询
    @Select("SELECT uid, nickname, name, gender, age," +
            " phone, email, balance ,role FROM user " +
            "limit #{current}, #{pageSize}")
    List<User> getUserList(int current, int pageSize);

    //注册
    @Insert("INSERT INTO user(uid,nickname, name, password, gender, age, email, phone) " +
            "VALUES(#{uid}, #{nickname}, #{name}, #{password}, #{gender}, #{age}, #{email}, #{phone})")
    int insertUser(User user);


    //更新用户信息
    @Update("UPDATE user SET nickname = #{nickname},  email = #{email}, phone = #{phone}, password = #{password} WHERE uid = #{uid}")
    int updateUser(User user);

    //删除用户
    @Update("DELETE FROM user WHERE uid = #{uid}")
    void deleteUser(String uid);

    //根据用户名查询用户信息
    @Select("SELECT * FROM user WHERE name = #{name}")
    User findUserByName(String name);

    //根据用户id查询用户信息
    @Select("SELECT  uid, nickname, name, gender, age, phone, email, balance, role FROM user WHERE uid = #{uid}")
    User findUserInfoByUid(String uid);

    //根据用户昵称查询用户信息
    @Select("SELECT * FROM user WHERE nickname = #{nickname}")
    User findUserByNickname(String nickname);

    //根据手机号查询用户信息
    @Select("SELECT * FROM user WHERE phone = #{phone}")
    User findUserByPhone(Long phone);

    //查询用户总数
    @Select("SELECT COUNT(*) FROM user")
    int countUser();

    @Select("SELECT * FROM user WHERE uid = #{id}")
    User findUserById(String id);

    @Update("UPDATE user SET balance = #{balance} WHERE uid = #{uid}")
    int updateBalance(String uid, double balance);
}
