package com.base.myFramework.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.base.myFramework.exception.ErrorCode;
import com.base.myFramework.exception.GlobalErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * @Package tv.yunxi.ctrip.api.base
 * @ClassName ApiResult
 * @Author suxin
 * @DATE 2019/11/25 下午10:14
 */
@ApiModel(description = "响应结果")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResult<T> implements Serializable {

    private static final long serialVersionUID = 1922539991804940811L;
    @ApiModelProperty("调用是否成功")
    @Builder.Default
    @JSONField(ordinal = 1)
    private boolean success = true;
    @ApiModelProperty(value = "状态码", required = true)
    @Builder.Default
    @JSONField(ordinal = 2)
    private Integer code = 200;
    @ApiModelProperty("调用结果消息")
    @Builder.Default
    @JSONField(ordinal = 3)
    private String message = "";
    @ApiModelProperty("成功时响应数据")
    @JSONField(ordinal = 4)
    private T data;
    @ApiModelProperty(value = "时间戳", required = true, dataType = "Long")
    @Builder.Default
    @JSONField(ordinal = 5)
    private Long timestamp = System.currentTimeMillis();

    /**
     *   
     *  * <p> 返回成功结果，无返回结果集 </p>
     *  
     *  * @return ApiResult<T>
     *  
     */
    public static <T> APIResult<T> ok() {
        return ok(null);
    }

    /**
     *   
     *  * <p> 返回成功结果，有返回结果集 </p>
     *  
     *  * @param data 返回结果集
     *  * @return ApiResult<T>
     *  
     */
    @SuppressWarnings("unchecked")
    public static <T> APIResult<T> ok(T data) {
        return ((APIResult<T>) APIResult.builder().data(data).build());
    }

    /**
     *   
     *  * <p> 返回失败结果，默认错误码与错误提示信息 </p>
     *  
     *  * @return ApiResult<T>
     *  
     */
    @SuppressWarnings("unchecked")
    public static <T> APIResult<T> error() {
        return ((APIResult<T>) APIResult.builder().success(false).code(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()).data(null).build());
    }

    /**
     *   
     *  * <p> 返回失败结果，默认错误码，指定错误提示信息 </p>
     *  
     *  * @param message 错误提示信息
     *  * @return ApiResult<T>
     *  
     */
    @SuppressWarnings("unchecked")
    public static <T> APIResult<T> error(String message) {
        return ((APIResult<T>) APIResult.builder().success(false).code(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(message).data(null).build());
    }

    /**
     *   
     *  * <p> 返回失败结果，指定错误码与错误提示信息 </p>
     *  
     *  * @param code
     *  * @param message
     *  * @return ApiResult<T>
     *  
     */
    @SuppressWarnings("unchecked")
    public static <T> APIResult<T> error(Integer code, String message) {
        return ((APIResult<T>) APIResult.builder().success(false).code(code).message(message).data(null).build());
    }

    /**
     *   
     *  * <p> 返回失败结果，根据{@link ErrorCode} 指定错误码与错误提示信息 </p>
     *  
     *  * @param errorCode {@link ErrorCode}
     *  * @return ApiResult<T>
     *  
     */
    @SuppressWarnings("unchecked")
    public static <T> APIResult<T> error(ErrorCode errorCode) {
        return ((APIResult<T>) APIResult.builder().success(false).code(errorCode.getCode())
                .message(errorCode.getMessage()).data(null).build());
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
