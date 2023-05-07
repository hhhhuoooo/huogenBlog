package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/3 16:31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {
    private String name;
    private String remark;
}
