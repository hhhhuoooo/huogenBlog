package com.huo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/4 10:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {
    private Long id;
    private String name;
    private String remark;

}
