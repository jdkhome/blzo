package com.jdkhome.blzo.user.service;

import com.jdkhome.blzo.user.generator.dao.UserMapper;
import com.jdkhome.blzo.user.generator.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * create by linkji.
 * create at 16:27 2019-10-28
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional
    public void addUser(String name, String phone) {

        User user = new User();
        user.setName(name);
        user.setPhone(phone);

        userMapper.insertSelective(user);
    }
}
