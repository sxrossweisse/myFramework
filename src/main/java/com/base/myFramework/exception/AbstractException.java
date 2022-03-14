package com.base.myFramework.exception;

/**
 * @Package tv.yunxi.ctrip.core.exception
 * @ClassName AbstractException
 * @Author suxin
 * @DATE 2019/11/25 下午10:06
 */
public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected Integer code;
    protected String msg;

    public AbstractException() {
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return msg;
    }

}
