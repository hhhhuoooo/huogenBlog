package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 17:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDto {
    private String name;
    private String status;
}
