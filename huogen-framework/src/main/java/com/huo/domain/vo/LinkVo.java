package com.huo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/27 15:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo{

    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;



}

