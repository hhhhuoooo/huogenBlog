package com.huo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 14:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectVo {
    //菜单ID
    private Long id;
    //菜单名称
    private String label;
    //父菜单ID
    private Long parentId;


    private List<MenuTreeSelectVo> children;


}
