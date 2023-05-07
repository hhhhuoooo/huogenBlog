package com.huo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/5/6 16:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {
    private String userName;
    private String phonenumber;
    private String status;


}
