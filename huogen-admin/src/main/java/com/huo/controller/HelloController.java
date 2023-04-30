package com.huo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/30 16:07
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public  String hello(){
        return "hello hg-admin";
    }
}
