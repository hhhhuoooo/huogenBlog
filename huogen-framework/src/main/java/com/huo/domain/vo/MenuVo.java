package com.huo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/4 20:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;
    //权限标识
    private String perms;
    //菜单图标
    private String icon;


//    //创建者
//    private Long createBy;
//    //创建时间
//    private Date createTime;
//    //更新者
//    private Long updateBy;
//    //更新时间
//    private Date updateTime;

    //备注
    private String remark;

}
