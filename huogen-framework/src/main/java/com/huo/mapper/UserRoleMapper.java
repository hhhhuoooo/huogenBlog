package com.huo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huo.domain.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户和角色关联表(UserRole)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-23 08:36:45
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}

