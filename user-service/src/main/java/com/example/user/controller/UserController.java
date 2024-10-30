package com.example.user.controller;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.example.api.utils.UserContext;
import com.example.user.config.JwtProperties;
import com.example.user.domain.vo.LoginUser;
import com.example.user.domain.vo.Result;
import com.example.user.domain.po.User;
import com.example.user.domain.vo.UserList;
import com.example.user.service.UserService;
import com.example.user.utils.RedisUtils;
import com.example.user.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final RedisUtils redisUtil;


    @Operation(summary = "查询用户信息")
    @GetMapping("/list")
    public Result<UserList> getUserList(
            @RequestParam(required = false) Long phone,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") int current,
            @RequestParam(required = false, defaultValue = "10") int pageSize) {
        UserList userList = new UserList();
        List<User> user;
        userList.setSize(pageSize);
        userList.setPage(current);
        userList.setCount(userService.countUser());

//        String userListByR = redisUtil.get(phone + nickname + name + current + pageSize);
//        if (userListByR != null){
//            user = JSON.parseObject(userListByR,new TypeReference<List<User>>(){});
//        }else{
            user = userService.findUser(phone, nickname, name, current, pageSize);
//            System.out.println("mysql" + user);
//            String json = JSON.toJSONString(user);
//            redisUtil.set(phone + nickname + name + current + pageSize, json);
//        }
        userList.setList(user);

        return Result.success(userList);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/getCurrentUser")
    public Result<User> getCurrentUser() {
        String uid = UserContext.getUserInfo();
        User user = userService.findUserInfoByUid(uid);
        System.out.println("getCurrentUser: " + user);
        return Result.success(user);
    }

    @Operation(summary = "新增用户信息")
    @PostMapping("/add")
    public Result<String> addUser(@RequestBody User user) {
        if (userService.findUserByName(user.getName()) != null) {
            return Result.error("用户名已存在");
        }
        if (userService.findUserByPhone(user.getPhone()) != null) {
            return Result.error("手机号已存在");
        }
        if (userService.findUserByNickname(user.getNickname()) != null) {
            return Result.error("昵称已存在");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setUid(UUID.randomUUID().toString());
        if (userService.addUser(user) == 1) {
            return Result.success("新增用户成功");
        }
        return Result.error("新增用户失败");
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/update")
    public Result<String> updateUser(@RequestBody User user) {
        User oldUser = userService.findUserInfoByUid(user.getUid());
        System.out.println("updateUser: " + oldUser);
        if (!oldUser.getPhone().equals(user.getPhone())) {
            if (userService.findUserByPhone(user.getPhone()) != null) {
                return Result.error("手机号已存在");
            }
        }
        if (!oldUser.getNickname().equals(user.getNickname())) {
            if (userService.findUserByNickname(user.getNickname()) != null) {
                return Result.error("昵称已存在");
            }
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if (userService.updateUser(user) == 1) {
            return Result.success("更新用户成功");
        }
        return Result.error("更新用户失败");
    }

    @Operation(summary = "删除用户信息")
    @DeleteMapping("/delete")
    public Result<String> deleteUser(@RequestParam String id) {
        userService.deleteUser(id);
        return Result.success("删除用户成功");
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginUser> login(
            @RequestBody User users,
            HttpServletResponse response) {
        User loginUser;
        Long phone = users.getPhone();
        String nickname = users.getNickname();
        String password = users.getPassword();
        if (phone == null) {
            loginUser = userService.findUserByNickname(nickname);
        } else if (nickname == null) {
            loginUser = userService.findUserByPhone(phone);
        } else {
            return Result.error("用户名或手机号不能为空");
        }

        if (loginUser == null) {
            return Result.error("用户不存在");
        }

        if (!BCrypt.checkpw(password, loginUser.getPassword())) {
            return Result.error("密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", loginUser.getUid());
        claims.put("name", loginUser.getName());
        claims.put("role", loginUser.getRole());
        String jwt = JwtUtils.createJwt(jwtProperties.getSecret(), jwtProperties.getExpiration(), claims);

        LoginUser user = new LoginUser();
        user.setUserId(loginUser.getUid());
        user.setName(loginUser.getName());
        user.setToken(jwt);
        user.setRole(loginUser.getRole());

        //将token放入请求头
        response.setHeader("Authorization", jwt);


        return Result.success("登录成功", user);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/changePassword")
    public Result<String> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        User user = userService.findUserById(UserContext.getUserInfo());
        System.out.println(user);
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            return Result.error("旧密码错误");
        }
        if (oldPassword.equals(newPassword)){
            return Result.error("新密码不能与旧密码相同");
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));

        if (userService.updateUser(user) == 1) {
            return Result.success("密码修改成功");
        }

        return Result.error("密码修改失败");
    }

    @GetMapping("/getUserByName")
    public Result<User> getUserByName(@RequestParam String name) {
        User user = userService.findUserByName(name);
        return Result.success(user);
    }

    @GetMapping("/getUserByPhone")
    public Result<User> getUserByPhone(@RequestParam Long phone) {
        User user = userService.findUserByPhone(phone);
        return Result.success(user);
    }
}

