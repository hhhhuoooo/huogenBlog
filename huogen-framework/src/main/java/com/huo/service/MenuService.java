package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.MenuListDto;
import com.huo.domain.entity.Menu;
import com.huo.domain.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-05-03 11:21:27
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult menuList(MenuListDto menuListDto);

    List<Menu> selectMenuList(Menu menu);

    boolean hasChild(Long menuId);

    List<Long> selectMenuListByRoleId(Long roleId);

    ResponseResult<MenuVo> add(Menu menu);

    ResponseResult getMenu(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenu(Long id);

}

