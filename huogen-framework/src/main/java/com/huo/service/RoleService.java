package com.huo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huo.domain.ResponseResult;
import com.huo.domain.dto.RoleAddDto;
import com.huo.domain.dto.RoleListDto;
import com.huo.domain.dto.RoleStatusDto;
import com.huo.domain.entity.Role;
import com.huo.domain.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-05-03 11:23:58
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

    ResponseResult<PageVo> roleList(Integer pageNum, Integer pageSize, RoleListDto roleListDto);

    @Transactional
    void insertRole(Role role);

    ResponseResult changeStatus(RoleStatusDto roleStatusDto);

    ResponseResult get(Long id);

    void updateRole(Role role);

//    ResponseResult add(RoleAddDto roleAddDto);

    ResponseResult listAllRole();

    List<Role> selectRoleAll();

    List<Long> selectRoleIdByUserId(Long userId);
}

