package com.base.myFramework.enums.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *   
 *  * <p> 接口错误码枚举 </p>
 *   
 *
 * @author suxin
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCodeEnum {

    NO_DATA(1001, "数据为空"),

    // 10 用户域
    LOGIN_IN_ERROR(2001, "请登录"),


    // 其他异常
    EXCEPTION(10000, "系统异常");

    private Integer code;
    private String message;
}
