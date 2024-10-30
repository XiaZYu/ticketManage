package com.example.api.client;

import com.example.api.po.Result;
import com.example.api.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.datatransfer.Clipboard;


@FeignClient("user-service")
public interface UserClient {

    @GetMapping("/api/users/getCurrentUser")
    Result<User> getCurrentUser();

    @GetMapping("/api/users/getUserByName")
    Result<User> getUserByName(@RequestParam("name") String name);

    @GetMapping("/api/users/getUserByPhone")
    Result<User> getUserByPhone(@RequestParam("phone") Long phone);
}
