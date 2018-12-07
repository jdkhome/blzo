package com.jdkhome.blzo.manage.common.aop.authj;

import java.lang.annotation.*;

/**
 * Created by jdk on 17/12/22.
 * 表记该方法受到Authj 监管
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authj {

    /**
     * value 字段描述该接口的名称
     *
     * @return
     */
    String value() default AuthjConstants.FROM_API;

    /**
     * 标记该接口是否需要鉴权
     * 默认是
     *
     * @return
     */
    boolean auth() default true;

    /**
     * 标记该接口是否可作为一个菜单
     * 默认否
     *
     * @return
     */
    boolean menu() default false;

    /**
     * 如果为true 则只要登录就有权限
     *
     * @return
     */
    boolean common() default false;


}
