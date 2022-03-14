package com.base.myFramework.exception;

/**
 * @Package com.base.myFramework.base
 * @ClassName ErrorCode
 * @Author suxin
 * @DATE 2019/11/26 上午1:28
 */
public interface ErrorCode {

    /**
     *   
     *  * <p> 获取错误码 </p>
     *  
     *  * @return
     *  
     */
    Integer getCode();

    /**
     *   
     *  * <p> 获取错误信息 </p>
     *  
     *  * @return
     *  
     */
    String getMessage();

}
