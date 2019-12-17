package com.jdkhome.blzo.friend.service;

import com.jdkhome.blzo.common.rpc.UserRPCService;
import com.jdkhome.blzo.friend.generator.dao.FriendMapper;
import com.jdkhome.blzo.friend.generator.model.Friend;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by linkji.
 * create at 16:22 2019-10-28
 */
@Slf4j
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    UserRPCService userRPCService;

    @Override
    //@Transactional
    @GlobalTransactional(name = "add-friend",rollbackFor = Exception.class)
    public void addFriend(String name, String phone) {

        // --- 添加用户 ---
        log.info("添加用户 start");
        userRPCService.apiUserAdd(name, phone);
        log.info("添加用户 end");

        Friend friend = new Friend();
        friend.setFname(name);
        friend.setFphone(phone);
        log.info("添加朋友 start");
        friendMapper.insertSelective(friend);
        log.info("添加朋友 end");
    }
}
