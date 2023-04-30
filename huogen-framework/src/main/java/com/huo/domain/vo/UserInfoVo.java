package com.huo.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/27 16:11
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}