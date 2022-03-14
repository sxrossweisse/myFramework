package com.base.myFramework.exception;

import java.io.Serializable;
import java.util.List;

/**
 * 校验结果
 */
public interface ValidateResults extends Serializable {

    boolean isSuccess();

    List<Result> getResults();

    interface Result extends Serializable {

        Integer getCode();

        String getField();

        String getMessage();

    }
}
