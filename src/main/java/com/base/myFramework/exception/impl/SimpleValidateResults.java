package com.base.myFramework.exception.impl;


import com.base.myFramework.exception.ValidateResults;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础校验结果
 */
public class SimpleValidateResults implements ValidateResults {

    private static final long serialVersionUID = -8334055567733526018L;

    private List<ValidateResults.Result> results = new ArrayList<>();

    public SimpleValidateResults addResult(Integer code, String field, String message) {
        results.add(new Result(code, field, message));
        return this;
    }

    @Override
    public boolean isSuccess() {
        return results == null || results.isEmpty();
    }

    @Override
    public List<ValidateResults.Result> getResults() {
        return results;
    }

    class Result implements ValidateResults.Result {
        private static final long serialVersionUID = 5878546897886206712L;

        private Integer code;
        private String field;
        private String message;

        public Result(Integer code, String field, String message) {
            this.code = code;
            this.field = field;
            this.message = message;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        @Override
        public String getField() {
            return field;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "{" + "\"code\":\"" + code + ", \"field\":\"" + field + '\"' + ", \"message:\"" + message + '\"' + '}';
        }
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return "success";
        }
        return results.toString();
    }

}
