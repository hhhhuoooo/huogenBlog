package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.UserAddDto;
import com.huo.domain.dto.UserDto;
import com.huo.domain.dto.UserListDto;
import com.huo.domain.entity.Role;
import com.huo.domain.entity.User;
import com.huo.domain.entity.UserRole;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.UserGetVo;
import com.huo.domain.vo.UserInfoVo;
import com.huo.enums.AppHttpCodeEnum;
import com.huo.handler.exception.SystemException;
import com.huo.mapper.RoleMapper;
import com.huo.mapper.UserMapper;
import com.huo.service.UserRoleService;
import com.huo.service.UserService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/28 18:31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleService userRoleService;

    
    @Override
    public ResponseResult userInfo() {
        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }



    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }


    @Override
    public ResponseResult<PageVo> userList(Integer pageNum, Integer pageSize, UserListDto userListDto) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(userListDto.getUserName()),User::getUserName,userListDto.getUserName());
        queryWrapper.like(StringUtils.hasText(userListDto.getPhonenumber()),User::getPhonenumber,userListDto.getPhonenumber());
        queryWrapper.like(StringUtils.hasText(userListDto.getStatus()),User::getStatus,userListDto.getStatus());
        Page page=new Page(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName))==0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber,user.getPhonenumber()))==0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail,user.getEmail()))==0;
    }

    @Override
    public ResponseResult changeStatus(UserDto userDto) {

        if (StringUtils.hasText(userDto.getUserId().toString())){

            User user = new User();
            user.setId(userDto.getUserId());
            user.setStatus(userDto.getStatus());
            updateById(user);
        }
        return ResponseResult.okResult();
    }



    @Override
    public ResponseResult add(User user) {
//        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);
//        User user = new User();
//        user.setUserName(userAddDto.getUserName());
//        user.setNickName(userAddDto.getNickName());
//        user.setPassword(userAddDto.getPassword());
//        user.setPhonenumber(userAddDto.getPhonenumber());
//        user.setEmail(userAddDto.getEmail());
//        user.setSex(userAddDto.getSex());
//        user.setStatus(userAddDto.getStatus());
//        userMapper.insert(user);
        if(user.getRoleIds()!=null&&user.getRoleIds().length>0){
            insertUserRole(user);
        }

        return ResponseResult.okResult();
    }




    @Override
    public ResponseResult deleteUser(Long id) {
        userMapper.deleteById(id);
        return ResponseResult.okResult();
    }

//    @Override
//    public ResponseResult get(Long id) {
//
//        List<String> roleIds = userMapper.getRoleIds(id);
//
//        List<Role> roles = roleMapper.selectList(null);
//
//        User user = userMapper.selectById(id);
//        UserAddDto userAddDto = BeanCopyUtils.copyBean(user, UserAddDto.class);
//
//
//        return ResponseResult.okResult(new UserGetVo(roleIds,roles,userAddDto));
//    }


    @Override
    public ResponseResult updateUser(User user) {

        // 删除用户与角色关联
        LambdaQueryWrapper<UserRole> userRoleUpdateWrapper = new LambdaQueryWrapper<>();
        userRoleUpdateWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(userRoleUpdateWrapper);

        // 新增用户与角色管理
        insertUserRole(user);
        // 更新用户信息
        updateById(user);

        return ResponseResult.okResult();
    }

    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }


}
