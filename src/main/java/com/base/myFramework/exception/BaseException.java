package com.base.myFramework.exception;

import com.base.myFramework.enums.error.ErrorCodeEnum;
import org.springframework.ui.ModelMap;

/**
 *   
 *  * @Description: 框架异常基类
 *   
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 2010571935949293672L;

    protected ErrorCodeEnum statusCode;

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected Integer code;

    public BaseException() {
    }

    public BaseException(ErrorCodeEnum statusCode) {
        this.statusCode = statusCode;
    }

    public BaseException(String message) {
        super(message);
        this.msg = message;
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.msg = cause == null ? null : cause.toString();
    }

    public BaseException(Integer code, String msg) {
        super(msg + "[" + code + "]");
        this.code = code;
        this.msg = msg;
    }

    public void handler(ModelMap modelMap) {
        modelMap.put("code", getCode());
        modelMap.put("msg", getMsg());
        modelMap.put("timestamp", System.currentTimeMillis());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取状态码
     *
     * @return
     */
    protected abstract ErrorCodeEnum getStatusCode();

}
