package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.RoleListDto;
import com.huo.domain.dto.RoleStatusDto;
import com.huo.domain.entity.Role;
import com.huo.domain.entity.RoleMenu;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.RoleVo;
import com.huo.mapper.RoleMapper;
import com.huo.service.RoleMenuService;
import com.huo.service.RoleService;
import com.huo.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 11:23:59
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        if (id == 1L){
            List<String> roleKeys=new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult<PageVo> roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto) {

        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleListDto.getRoleName()), Role::getRoleName,roleListDto.getRoleName());
        queryWrapper.like(StringUtils.hasText(roleListDto.getStatus()), Role::getStatus,roleListDto.getStatus());
        queryWrapper.orderByAsc(Role::getRoleSort);

        Page page=new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);

        PageVo pageVo=new PageVo(roleVos,page.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult get(Long id) {
        if (StringUtils.hasText(id.toString())){
            Role role = roleMapper.selectById(id);
            RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
            return ResponseResult.okResult(roleVo);
        }

        return null;
    }

    @Override
    public void updateRole(Role role) {
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        insertRoleMenu(role);
    }

//    @Override
//    public ResponseResult add(RoleAddDto roleAddDto) {
//        //        3、将所得值传入数据库
//        Role role=new Role();
//        role.setRoleName(roleAddDto.getRoleName());
//        role.setRoleKey(roleAddDto.getRoleKey());
//        role.setRoleSort(roleAddDto.getRoleSort());
//        role.setStatus(roleAddDto.getStatus());
//        role.setRemark(roleAddDto.getRemark());
////      menusId
//        roleMapper.insert(role);
//        return  ResponseResult.okResult();
//    }

    @Override
    @Transactional
    public void insertRole(Role role) {
        save(role);
        if (role.getMenuIds()!=null&&role.getMenuIds().length>0){
            insertRoleMenu(role);
        }
    }




    @Override
    public ResponseResult changeStatus(RoleStatusDto roleStatusDto) {

        if (StringUtils.hasText(roleStatusDto.getRoleId().toString())){

            Role role = new Role();
            role.setId(roleStatusDto.getRoleId());
            role.setStatus(roleStatusDto.getStatus());
            updateById(role);
        }
        return ResponseResult.okResult();
    }


    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, SystemConstants.NORMAL);
        return ResponseResult.okResult(list(queryWrapper));
    }


    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstants.NORMAL));
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long userId) {
        return getBaseMapper().selectRoleIdByUserId(userId);
    }

    private void insertRoleMenu(Role role) {
        List<RoleMenu> roleMenuList = Arrays.stream(role.getMenuIds())
                .map(memuId -> new RoleMenu(role.getId(), memuId))
                .collect(Collectors.toList());
        roleMenuService.saveBatch(roleMenuList);
    }






}

