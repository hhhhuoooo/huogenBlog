package com.huo.handler.exception;

import com.huo.enums.AppHttpCodeEnum;

/**
 * @version 1.0
 * @Author huohuozi
 * @Date 2023/4/28 20:41
 */

public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
