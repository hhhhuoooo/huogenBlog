package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.MenuListDto;
import com.huo.domain.entity.Menu;
import com.huo.domain.entity.Tag;
import com.huo.domain.vo.MenuVo;
import com.huo.domain.vo.TagVo;
import com.huo.enums.AppHttpCodeEnum;
import com.huo.mapper.MenuMapper;
import com.huo.service.MenuService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.SecurityUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 11:21:28
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果id为1就返回所有的权限
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Menu::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
            queryWrapper.in(Menu::getMenuType,SystemConstants.MENU_TYPE_C,SystemConstants.MENU_TYPE_F);
            List<Menu> menus = list(queryWrapper);

            List<String> permissions=new ArrayList<>();
            for (Menu menu:menus) {
                String perms = menu.getPerms();
                permissions.add(perms);
            }
            return permissions;
        }
        //否则返回对应id的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }


    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }



    @Override
    public ResponseResult menuList(MenuListDto menuListDto) {
        //模糊查询
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(menuListDto.getStatus()), Menu::getStatus,menuListDto.getStatus());
        queryWrapper.like(StringUtils.hasText(menuListDto.getMenuName()),Menu::getMenuName,menuListDto.getMenuName());
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);;
        return menus;
    }

    @Override
    public boolean hasChild(Long menuId) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId,menuId);
        return count(queryWrapper) != 0;
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return getBaseMapper().selectMenuListByRoleId(roleId);
    }



    @Override
    public ResponseResult<MenuVo> add(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenu(Long id) {
        if (StringUtils.hasText(id.toString())){
            Menu menu = menuMapper.selectById(id);
            MenuVo MenuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
            return ResponseResult.okResult(MenuVo);
        }

        return null;
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        menuMapper.updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteMenu(Long id) {
        if (StringUtils.hasText(id.toString())&& menuMapper.selectById(id).getChildren() == null){
            menuMapper.deleteById(id);
            return ResponseResult.okResult();
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_DELETE);
        }
    }

//    @Override
//    public ResponseResult treeSelect() {
//        List<Menu> menus = list();
//        List<Menu> menuTree = builderMenuTree(menus,0L);
//        List<MenuTreeSelectVo> menuTreeSelectVos = BeanCopyUtils.copyBeanList(menuTree, MenuTreeSelectVo.class);
//
//        for (Menu menu:menuTree) {
//            for (MenuTreeSelectVo menuTreeSelectVo:menuTreeSelectVos) {
//                if (menu.getChildren().isEmpty()){
//                    menuTreeSelectVo.setLabel(menu.getMenuName());
//                    break;
//                }else {
//                    List<Menu> children = menu.getChildren();
//
//
//                }
//
//            }
//
//        }
//        return ResponseResult.okResult(menuTreeSelectVos);
//    }
}

