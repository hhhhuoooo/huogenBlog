package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.RoleStatusDto;
import com.huo.domain.dto.UserAddDto;
import com.huo.domain.dto.UserDto;
import com.huo.domain.dto.UserListDto;
import com.huo.domain.entity.Role;
import com.huo.domain.entity.User;
import com.huo.domain.vo.PageVo;

import java.util.List;

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


    ResponseResult add(User user);

    ResponseResult deleteUser(Long id);

//    ResponseResult get(Long id);


    ResponseResult updateUser(User user);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    ResponseResult changeStatus(UserDto userDto);
}
