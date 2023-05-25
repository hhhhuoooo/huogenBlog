package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.*;
import com.huo.domain.entity.Role;
import com.huo.domain.entity.User;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.TagVo;
import com.huo.domain.vo.UserGetVo;
import com.huo.enums.AppHttpCodeEnum;
import com.huo.handler.exception.SystemException;
import com.huo.service.RoleService;
import com.huo.service.TagService;
import com.huo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private RoleService roleService;

    //显示所有用户
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, UserListDto userListDto){
        return userService.userList(pageNum,pageSize,userListDto);
    }

    //修改用户状态
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody UserDto userDto){
        return userService.changeStatus(userDto);
    }


    //新增用户
    @PostMapping
    public ResponseResult add(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.add(user);
//        return userService.add(userAddDto);
    }


    //删除用户
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

//    //  获取用户信息
//    @GetMapping("/{id}")
//    public ResponseResult get(@PathVariable Long id){
//        return userService.get(id);
//    }

   //根据用户编号获取详细信息
    @GetMapping("/{userId}")
    public ResponseResult getUserInfoAndRoleIds(@PathVariable Long userId)
    {
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        //当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);

        UserGetVo vo = new UserGetVo(user,roles,roleIds);
        return ResponseResult.okResult(vo);
    }

    //修改用户信息
    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}
