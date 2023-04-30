package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.entity.User;
import com.huo.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/27 15:47
 */
@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        return blogLoginService.login(user);
    }


    @PostMapping("/logout")
    public ResponseResult logout() {
        return blogLoginService.logout();
    }
}
