package com.huo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huo.domain.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-23 08:34:06
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

