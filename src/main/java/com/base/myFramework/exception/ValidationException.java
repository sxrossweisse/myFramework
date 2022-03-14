package com.base.myFramework.exception;


import com.base.myFramework.exception.impl.SimpleValidateResults;

import java.util.List;

/**
 * @Package tv.yunxi.ctrip.core.exception
 * @ClassName ValidationException
 * @Author suxin
 * @DATE 2019/11/26 上午1:41
 */
public class ValidationException extends AbstractException {

    private static final long serialVersionUID = -5190169526412839535L;

    private SimpleValidateResults results;

    public ValidationException(SimpleValidateResults results) {
        super();
        this.code = 400;
        this.msg = results.toString();
        this.results = results;
    }

    public List<ValidateResults.Result> getResults() {
        if (results == null) {
            return null;
        }
        return results.getResults();
    }


}
