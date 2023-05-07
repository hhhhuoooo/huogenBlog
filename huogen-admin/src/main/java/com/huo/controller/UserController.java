package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.dto.UserAddDto;
import com.huo.domain.dto.UserListDto;
import com.huo.domain.entity.User;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.TagVo;
import com.huo.service.TagService;
import com.huo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 15:32
 */
@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private UserService userService;

    //显示所有用户
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.userList(pageNum,pageSize,userListDto);
    }



    //新增用户
    @PostMapping
    public ResponseResult add(@RequestBody UserAddDto userAddDto){
        return userService.add(userAddDto);
    }


    //删除用户
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    //  获取用户信息
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable Long id){
        return userService.get(id);
    }

    //修改用户信息
    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
