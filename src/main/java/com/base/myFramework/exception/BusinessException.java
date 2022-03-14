package com.base.myFramework.exception;

import com.base.myFramework.enums.error.ErrorCodeEnum;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;

@NoArgsConstructor
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ErrorCodeEnum statusCode) {
        super(statusCode);
    }

    public BusinessException(ErrorCode errorCode, Object... params) {
        super(errorCode.getCode(), MessageFormat.format(errorCode.getMessage(), params));
    }

    public BusinessException(Integer code, String message) {
        super(code, message);
    }

    public BusinessException(Exception e) {
        super(e);
    }

    @Override
    protected ErrorCodeEnum getStatusCode() {
        return super.statusCode != null ? super.statusCode : ErrorCodeEnum.EXCEPTION;
    }
}
