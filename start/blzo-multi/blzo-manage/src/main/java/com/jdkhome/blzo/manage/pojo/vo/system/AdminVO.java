package com.jdkhome.blzo.manage.pojo.vo.system;

import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import lombok.Data;

/**
 * author link.ji
 * createTime 上午10:12 2018/12/7
 */
@Data
public class AdminVO extends Admin {

    /**
     * 状态文字
     */
    String statusStr;

    /**
     * ip对应城市
     */
    String ipCity;

}
