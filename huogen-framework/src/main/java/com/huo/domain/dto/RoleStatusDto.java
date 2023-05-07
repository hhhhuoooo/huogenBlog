package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 11:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleStatusDto {
    private Long roleId;
    private String status;
}
