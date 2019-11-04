package com.jdkhome.blzo.service;

import com.jdkhome.blzo.generator.model.User;

/**
 * create by linkji.
 * create at 18:01 2019-10-30
 * [示例]用户业务接口
 */
public interface UserService {

    /**
     * 添加用户
     *
     * @param username
     * @param phone
     */
    void add(String username, String phone);

    /**
     * 查找用户
     * @param username
     * @return
     */
    User find(String username);
}
