package com.example.api.client;

import com.example.api.po.Result;
import com.example.api.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.awt.datatransfer.Clipboard;

@Service
@Component
@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/api/users/getCurrentUser")
    Result<User> getCurrentUser();

    @GetMapping("/api/users/getUserByName")
    Result<User> getUserByName(@RequestParam("name") String name);

    @GetMapping("/api/users/getUserByPhone")
    Result<User> getUserByPhone(@RequestParam("phone") Long phone);

    @PostMapping("/api/users/reduceBalance")
    Result<String> reduceBalance(@RequestParam("uid") String uid, @RequestParam("price") Double price);

    @PostMapping("/api/users/addBalance")
    Result<String> addBalance(@RequestParam("uid") String uid, @RequestParam("price") Double price);
}
