package com.huo.service;

import com.huo.domain.ResponseResult;
import com.huo.domain.entity.User;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/30 17:32
 */
public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
