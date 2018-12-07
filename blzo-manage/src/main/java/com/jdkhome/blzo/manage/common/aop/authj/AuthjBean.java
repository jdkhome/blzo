package com.jdkhome.blzo.manage.common.aop.authj;

import lombok.Data;

/**
 * Created by jdk on 17/12/22.
 * 权限对象
 */
@Data
public class AuthjBean {

    /**
     * 代表URI
     */
    String uri;

    /**
     * 权限对象名称
     */
    String name;

    /**
     * 是否需要鉴权
     */
    Boolean auth;

    /**
     * 是否可作为菜单
     */
    Boolean menu;

    /**
     * 如果为true 则只要登录用户就有权限
     */
    Boolean common;

    public AuthjBean(String uri, Authj authj) {
        this.uri = uri;
        this.name = authj.value();
        this.auth = authj.auth();
        this.menu = authj.menu();
        this.common = authj.common();
    }

    public AuthjBean(String uri, Authj authj, String name) {
        this.uri = uri;
        this.name = name;
        this.auth = authj.auth();
        this.menu = authj.menu();
        this.common = authj.common();
    }

}
