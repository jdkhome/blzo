package com.jdkhome.blzo.common.constants;

/**
 * author link.ji
 * createTime 上午11:08 2018/6/20
 * 正则常量
 */
public interface RegexConstant {

    /**
     * 正则表达式：验证手机号
     */
    String PHONE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

}
