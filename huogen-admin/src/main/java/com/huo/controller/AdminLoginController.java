package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.entity.LoginUser;
import com.huo.domain.entity.Menu;
import com.huo.domain.entity.User;
import com.huo.domain.vo.AdminUserInfoVo;
import com.huo.domain.vo.RoutersVo;
import com.huo.domain.vo.UserInfoVo;
import com.huo.service.AdminLoginService;
import com.huo.service.BlogLoginService;
import com.huo.service.MenuService;
import com.huo.service.RoleService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.RedisCache;
import com.huo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/27 15:47
 */
@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisCache redisCache;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return adminLoginService.login(user);
    }

    //退出登录
    @PostMapping("/user/logout")
    public  ResponseResult logout(){
        return adminLoginService.logout();
    }



    //动态路由功能的实现
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());

        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
//        List<String> roleKeyList =  null;

        //根据用户id查询角色信息

        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo=new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public  ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }










}
