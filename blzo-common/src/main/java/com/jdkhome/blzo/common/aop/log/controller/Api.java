package com.jdkhome.blzo.common.aop.log.controller;

import java.lang.annotation.*;

/**
 * Created by jdk on 17/4/20.
 * Controller层的日志
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

    /**
     * 接口名称
     *
     * @return
     */
    String value();

    /**
     * 是否需要打印日志
     *
     * @return
     */
    boolean log() default true;

}
