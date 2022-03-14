package com.base.myFramework.exception;

public enum GlobalErrorCode implements ErrorCode {

    /**
     * 系统异常
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private Integer code;

    private String message;

    GlobalErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
