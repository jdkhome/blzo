package com.jdkhome.blzo.manage.common.aop.authj;

import lombok.Data;

import java.util.List;

/**
 * Created by jdk on 18/1/8.
 * 菜单VO
 */
@Data
public class MenuVO {
    String name;
    List<AuthjBean> menus;


}
