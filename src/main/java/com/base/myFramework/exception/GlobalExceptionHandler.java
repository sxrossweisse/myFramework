package com.base.myFramework.exception;

import com.base.myFramework.common.APIResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public APIResult defaultException(Exception e) {
        log.error("系统内部错误: ", e);
        return APIResult.error();
    }

    /**
     * 业务异常.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public APIResult businessException(BusinessException ex) {
        log.error("业务异常 => ", ex);
        return APIResult.error(ex.getCode(), ex.getMsg());
    }

    /**
     * ValidException 异常处理
     */
    @ExceptionHandler(ValidationException.class)
    public APIResult validationExceptionHandler(ValidationException validEx) {
        log.error("参数校验异常=>{}", validEx.getResults());
        return APIResult.error(validEx.getCode(), validEx.getMessage());
    }

}
