package com.jdkhome.blzo.manage.pojo.vo.mine;


import com.jdkhome.blzo.manage.generator.model.Admin;

/**
 * Created by jdk on 18/1/8.
 */
public class GroupAdminVO extends Admin {
    public Boolean getHave() {
        return have;
    }

    public void setHave(Boolean have) {
        this.have = have;
    }

    Boolean have;
}
