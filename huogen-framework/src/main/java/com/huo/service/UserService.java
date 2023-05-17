package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.RoleStatusDto;
import com.huo.domain.dto.UserAddDto;
import com.huo.domain.dto.UserDto;
import com.huo.domain.dto.UserListDto;
import com.huo.domain.entity.User;
import com.huo.domain.vo.PageVo;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/28 18:31
 */
public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult<PageVo> userList(Integer pageNum, Integer pageSize, UserListDto userListDto);


    ResponseResult add(UserAddDto userAddDto);

    ResponseResult deleteUser(Long id);

    ResponseResult get(Long id);

    ResponseResult updateUser(User user);

    ResponseResult changeStatus(UserDto userDto);
}
