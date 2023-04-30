package com.huo.service;

import com.huo.domain.ResponseResult;
import com.huo.domain.entity.User;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/27 15:49
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
