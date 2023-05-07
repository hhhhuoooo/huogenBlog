package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 18:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkAddDto{
    private String status;
    private String name;
    private String description;
    private String logo;
    private String address;

}
