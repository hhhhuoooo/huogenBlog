package com.huo.controller;

import com.huo.annotation.SystemLog;
import com.huo.domain.ResponseResult;
import com.huo.domain.entity.User;
import com.huo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/28 21:01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


//    获取用户信息
    @GetMapping(    "/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }


    //更新用户信息
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }


//    注册用户
    @PostMapping ("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
