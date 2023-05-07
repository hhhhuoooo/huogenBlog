package com.huo.domain.dto;

import com.huo.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 15:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAddDto {
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;

    private List<String> menuIds;

    //备注
    private String remark;

}
