package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 11:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleListDto {
    //角色名称
    private String roleName;
    //状态
    private String status;

}
