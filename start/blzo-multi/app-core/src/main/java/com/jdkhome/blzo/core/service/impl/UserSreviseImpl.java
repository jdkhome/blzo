package com.jdkhome.blzo.core.service.impl;

import com.jdkhome.blzo.common.generator.dao.UserMapper;
import com.jdkhome.blzo.common.generator.model.User;
import com.jdkhome.blzo.common.generator.model.UserExample;
import com.jdkhome.blzo.core.common.enums.RespError;
import com.jdkhome.blzo.core.service.UserService;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * create by linkji.
 * create at 18:01 2019-10-30
 */
@Slf4j
@Service
public class UserSreviseImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    /**
     * 添加用户
     *
     * @param username
     * @param phone
     */
    @Override
    public void add(String username, String phone) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(phone)) {
            log.error("添加用户 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        if (this.find(username) != null) {
            log.error("添加用户 -> 用户已存在");
            throw new ServiceException(RespError.USER_ALREADY_EXISTS);
        }

        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);

        userMapper.insertSelective(user);
    }

    /**
     * 查找用户
     *
     * @param username
     * @return
     */
    @Override
    public User find(String username) {

        if (StringUtils.isEmpty(username)) {
            log.error("查找用户 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);

        var list = userMapper.selectByExample(example);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}
