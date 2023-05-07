package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 18:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {
    private String name;
    private String status;
    private String description;
    private Long id;


}
