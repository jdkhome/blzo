package com.jdkhome.blzo.common.enums;


import com.jdkhome.blzo.ex.basic.enums.I18nEnums;
import com.jdkhome.blzo.ex.basic.pojo.BaseError;
import lombok.Getter;

/**
 * author link.ji
 * createTime 上午11:13 2018/6/20
 * 异常响应枚举
 */
@Getter
public enum RespError implements BaseError {

    USER_ALREADY_EXISTS(100001, "用户已存在"),

    ;

    private Integer code;
    private String msg;

    @Override
    public String getMsg(I18nEnums i18n) {
        // 你可以switch i18n 以此来实现响应消息的多语言
        return msg;
    }

    RespError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
