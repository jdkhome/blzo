package com.jdkhome.blzo.common.enums;

import com.jdkhome.blzo.common.pojo.BaseError;

/**
 * author link.ji
 * createTime 下午7:28 2018/6/21
 */
public enum CommonResponseError implements BaseError {

    //===========通用返回==============
    NO_LOGIN(100, "请先登录"),
    VERSION_UPGRADE(101, "需要升级版本"),
    SUCCESS(200, "success"),
    PARAMETER_ERROR(400, "参数错误"),
    NO_PERMISSION(403, "权限不足"),
    SERVER_ERROR(500, "服务器未知错误"),
    UPSTREAM_ERROR(600, "上游错误");

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


    CommonResponseError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
