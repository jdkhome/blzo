package com.jdkhome.blzo.common.exception;


import com.jdkhome.blzo.common.pojo.BaseError;

/**
 * author link.ji
 * createTime 下午5:38 2018/6/21
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    //异常码
    private final Integer errorCode;

    //异常信息
    private final String errorMsg;

    //更多信息
    private final Object data;

    //返回给前端的错误信息
    private final BaseError baseError;

    public BaseError getResponseError() {
        return baseError;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Object getData() {
        return data;
    }

    public ServiceException() {
        this.errorCode = null;
        this.errorMsg = null;
        this.data = null;
        this.baseError = null;
    }

    public ServiceException(BaseError baseError) {
        this.errorCode = baseError.getCode();
        this.errorMsg = baseError.getMsg();
        this.data = null;
        this.baseError = baseError;
    }

    public ServiceException(BaseError baseError, Object data) {
        this.errorCode = baseError.getCode();
        this.errorMsg = baseError.getMsg();
        this.data = data;
        this.baseError = baseError;
    }

    public ServiceException(Integer errorCode, String errorMsg, Object data, BaseError baseError) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
        this.baseError = baseError;
    }


}
