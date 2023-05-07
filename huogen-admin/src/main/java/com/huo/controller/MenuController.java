package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.MenuListDto;
import com.huo.domain.entity.Menu;
import com.huo.domain.vo.MenuVo;
import com.huo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/treeselect")
    public ResponseResult treeSelect(){
        return menuService.treeSelect();
    }
}
