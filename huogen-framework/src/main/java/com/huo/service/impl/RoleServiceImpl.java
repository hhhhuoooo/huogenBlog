package com.huo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huo.constants.SystemConstants;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.RoleAddDto;
import com.huo.domain.dto.RoleListDto;
import com.huo.domain.dto.RoleStatusDto;
import com.huo.domain.entity.Role;
import com.huo.domain.entity.Tag;
import com.huo.domain.entity.User;
import com.huo.domain.vo.PageVo;
import com.huo.domain.vo.RoleVo;
import com.huo.domain.vo.TagVo;
import com.huo.mapper.RoleMapper;
import com.huo.service.RoleService;
import com.huo.utils.BeanCopyUtils;
import com.huo.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseResult add(RoleAddDto roleAddDto) {
        //        3、将所得值传入数据库
        Role role=new Role();
        role.setRoleName(roleAddDto.getRoleName());
        role.setRoleKey(roleAddDto.getRoleKey());
        role.setRoleSort(roleAddDto.getRoleSort());
        role.setStatus(roleAddDto.getStatus());
        role.setRemark(roleAddDto.getRemark());
//      menusId
        roleMapper.insert(role);
        return  ResponseResult.okResult();
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





}

