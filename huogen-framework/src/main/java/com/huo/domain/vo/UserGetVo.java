package com.huo.domain.vo;

import com.huo.domain.dto.UserAddDto;
import com.huo.domain.entity.Role;
import com.huo.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 16:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetVo {
    private User user;

    private List<Role> roles;

    private List<Long> roleIds;
}
