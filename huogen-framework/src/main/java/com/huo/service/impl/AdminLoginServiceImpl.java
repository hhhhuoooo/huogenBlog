package com.huo.service.impl;

import com.huo.domain.ResponseResult;
import com.huo.domain.entity.LoginUser;
import com.huo.domain.entity.User;
import com.huo.domain.vo.BlogUserLoginVo;
import com.huo.domain.vo.UserInfoVo;
import com.huo.service.AdminLoginService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.JwtUtil;
import com.huo.utils.RedisCache;
import com.huo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/30 17:32
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

//        //把token和userinfo封装 返回
//        //把User转换成UserInfoVo
//        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
//        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);

    }

    @Override
    public ResponseResult logout() {
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的值
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
