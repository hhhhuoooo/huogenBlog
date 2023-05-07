package com.huo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huo.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-27 15:40:01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<String> getRoleIds(Long id);
}

