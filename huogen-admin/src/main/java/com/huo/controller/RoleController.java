package com.huo.controller;

import com.huo.domain.ResponseResult;
import com.huo.domain.dto.RoleAddDto;
import com.huo.domain.dto.RoleListDto;
import com.huo.domain.dto.RoleStatusDto;
import com.huo.domain.dto.TagListDto;
import com.huo.domain.entity.Role;
import com.huo.domain.vo.PageVo;
import com.huo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 11:03
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;


    //查询角色列表
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, RoleListDto roleListDto){
        return roleService.roleList(pageNum,pageSize,roleListDto);
    }



    //修改角色状态
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto roleStatusDto){
        return roleService.changeStatus(roleStatusDto);
    }


    //角色信息回显接口
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable Long id){
        return roleService.get(id);
    }


    @PostMapping
    public ResponseResult add(@RequestBody RoleAddDto roleAddDto){
        return roleService.add(roleAddDto);
    }


    ////查询的是所有状态正常的角色
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }









}
