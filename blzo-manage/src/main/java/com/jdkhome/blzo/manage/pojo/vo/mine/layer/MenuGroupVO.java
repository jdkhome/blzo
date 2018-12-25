package com.jdkhome.blzo.manage.pojo.vo.mine.layer;

import com.jdkhome.blzo.ex.authj.core.AuthjBean;
import lombok.Data;

import java.util.List;

/**
 * author link.ji
 * createTime 下午6:15 2018/12/5
 */
@Data
public class MenuGroupVO {
    /**
     * 组名
     */
    String name;

    /**
     * 菜单权限实体
     */
    List<AuthjBean> menus;
}
