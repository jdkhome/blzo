package com.jdkhome.blzo.common.constants;

/**
 * author link.ji
 * createTime 下午9:06 2018/7/18
 */
public interface RegularExpression {

    /**
     * 正则表达式：验证用户名
     */
    String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 昵称
     */
    String REGEX_NICKNAME="^[\\d\\w\\u4e00-\\u9fa5,\\.;\\:\"'?!\\-]{2,15}$";

    /**
     * 6位数字验证码
     */
    String REGEX_CAPTCHA = "[A-Z0-9]{6}";

    /**
     * 验证码类型格式 2到15位 小写字母加下划线
     */
    String REGEX_CAPTCHA_TYPE = "[a-z_]{2,15}";

    /**
     * 正则表达式：验证密码
     */
    String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 正则表达式：验证手机号
     */
    String REGEX_PHONE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证URL
     */
    String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式：验证IP地址
     */
    String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
}
