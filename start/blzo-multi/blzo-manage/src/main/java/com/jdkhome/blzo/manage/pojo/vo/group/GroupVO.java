package com.jdkhome.blzo.manage.pojo.vo.group;

import com.jdkhome.blzo.ex.authj.core.AuthjBean;
import lombok.Data;

import java.util.List;

/**
 * author link.ji
 * createTime 下午4:01 2018/12/6
 */
@Data
public class GroupVO {

    Integer id;

    String name;

    String createAdminName;

    List<AuthjBean> auths;

}
