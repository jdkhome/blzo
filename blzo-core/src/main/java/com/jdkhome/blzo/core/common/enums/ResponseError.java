package com.jdkhome.blzo.core.common.enums;


import com.jdkhome.blzo.common.pojo.BaseError;

/**
 * author link.ji
 * createTime 上午11:13 2018/6/20
 */
public enum ResponseError implements BaseError {

    XXXXX_ERROR(66666, "XXXXX业务异常");


    private Integer code;
    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }


    ResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
