package com.jdkhome.blzo.manage.common.aop.authj;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created by jdk on 18/1/6.
 * 用户权限配置实体
 */
@Data
public class UserAuthjConfBean {

    Integer id;

    String name;

    Set<String> authUriSet;

    List<MenuVO> layerList;

    MenuVO moreMenu;

}
