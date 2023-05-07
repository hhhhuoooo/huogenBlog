package com.huo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 11:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {
    //角色ID
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;

    //创建时间
    private Date createTime;

}
