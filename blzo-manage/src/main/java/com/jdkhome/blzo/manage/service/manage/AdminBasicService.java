package com.jdkhome.blzo.manage.service.manage;


import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.manage.common.aop.authj.menu.LayerDTO;
import com.jdkhome.blzo.manage.generator.model.Admin;

import java.util.List;

/**
 * Created by jdk on 17/11/16.
 * 管理员管理业务接口
 */
public interface AdminBasicService {

    //============== 添加 ==============//


    /**
     * 添加管理员
     *
     * @param username 登录名
     * @param password 密码
     * @param nickName
     * @param phone
     * @param remark
     * @return
     */
    Integer addAdmin(String username, String password, String nickName, String phone, String remark);

    //============== 修改 ==============//

    /**
     * 修改管理员
     *
     * @param adminId
     * @param username 登录名
     * @param password 密码
     * @param nickName
     * @param phone
     * @param remark
     * @param layer
     * @return
     */
    Integer editAdmin(Integer adminId, String username, String password, String nickName, String phone, Integer status, String remark, List<LayerDTO> layer);

    //============== 删除 ==============//

    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    Integer delAdmin(Integer adminId);

    //============== 查询接口 ==============//

    /**
     * 获取管理员通过Id
     *
     * @param adminId
     * @return
     */
    Admin getAdminById(Integer adminId);

    /**
     * 获取管理员通过username
     *
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

    /**
     * 分页查询管理员
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo<Admin> getAdminsWithPage(String username, String nickName, String phone, Integer page, Integer size);

    /**
     * 获取所有管理员
     *
     * @return
     */
    List<Admin> getAllAdmin(String username, String nickName, String phone);


}
