package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.MenuListDto;
import com.huo.domain.entity.Menu;
import com.huo.domain.vo.MenuTreeVo;
import com.huo.domain.vo.MenuVo;
import com.huo.domain.vo.RoleMenuTreeSelectVo;
import com.huo.service.MenuService;
import com.huo.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/4 19:52
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;


    //展示所有的菜单
    @GetMapping("/list")
    public ResponseResult<MenuVo> list(MenuListDto menuListDto){
        return  menuService.menuList(menuListDto);
    }


    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeSelect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(checkedKeys,menuTreeVos);
        return ResponseResult.okResult(vo);
    }
    //新增菜单
    @PostMapping
    public ResponseResult<MenuVo> add(@RequestBody Menu menu){
        return  menuService.add(menu);
    }


    //获取菜单的数据
    @GetMapping("/{id}")
    public ResponseResult getMenu(@PathVariable Long id){
        return  menuService.getMenu(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return  menuService.updateMenu(menu);
    }


    //删除菜单
    @DeleteMapping("/{id}")
    public ResponseResult deleteMenu(@PathVariable Long id){
        return  menuService.deleteMenu(id);
    }



//    @GetMapping("/treeselect")
//    public ResponseResult treeSelect(){
//        return menuService.treeSelect();
//    }
}
