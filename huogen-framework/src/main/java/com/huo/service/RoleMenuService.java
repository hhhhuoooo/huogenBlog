package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.entity.RoleMenu;


/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-05-23 08:34:10
 */
public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}

