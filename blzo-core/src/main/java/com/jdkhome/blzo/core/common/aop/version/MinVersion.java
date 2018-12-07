package com.jdkhome.blzo.core.common.aop.version;

import java.lang.annotation.*;

/**
 * author link.ji
 * createTime 下午5:09 2018/6/19
 * 加了version注解的接口，必须在header中传入version="x.x.x"
 * 没有传入version 或者version版本过老的接口，将会直接返回"需要升级版本"
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinVersion {

    /**
     * 最小支持版本
     *
     * @return
     */
    String value();
}
