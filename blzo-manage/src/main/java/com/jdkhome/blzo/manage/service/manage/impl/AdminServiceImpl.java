package com.jdkhome.blzo.manage.service.manage.impl;

import com.jdkhome.blzo.common.component.coder.PasswordEncoder;
import com.jdkhome.blzo.common.component.generator.SaltGenerator;
import com.jdkhome.blzo.common.component.generator.UUIDGenerator;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.exception.ServiceException;
import com.jdkhome.blzo.manage.common.aop.authj.AuthjConstants;
import com.jdkhome.blzo.manage.common.enums.AdminStatusEnum;
import com.jdkhome.blzo.manage.common.enums.ResponseError;
import com.jdkhome.blzo.manage.generator.dao.AdminMapper;
import com.jdkhome.blzo.manage.generator.model.Admin;
import com.jdkhome.blzo.manage.generator.model.AdminExample;
import com.jdkhome.blzo.manage.service.manage.AdminBasicService;
import com.jdkhome.blzo.manage.service.manage.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jdk on 17/12/6.
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    AdminMapper adminMapper;

    /**
     * 管理员登录
     *
     * @param username
     * @param password
     * @param ip
     * @return
     */
    @Override
    public Admin login(String username, String password, String ip) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(ip)) {
            log.error("管理员登录->登录必要参数为空");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        // 获取对应管理员
        Admin admin = adminBasicService.getAdminByUsername(username);
        if (admin == null) {
            log.error("管理员登录->管理员不存在");
            throw new ServiceException(ResponseError.RESP_ERROR_ADMIN_NOT_EXIST);
        }

        //密码验证
        if (!admin.getPassword().equals(PasswordEncoder.toMD5(password, admin.getSalt()))) {
            log.error("管理员登录->密码错误");
            throw new ServiceException(ResponseError.RESP_ERROR_PASSWORD_ERROR);
        }

        //更新ip
        admin.setLastIp(ip);
        admin.setLastTime(new Date());

        adminMapper.updateByPrimaryKeySelective(admin);

        return admin;
    }

    /**
     * 如果没有root用户，则自动初始化一个
     */
    @Override
    public void initRoot() {

        Admin admin = adminBasicService.getAdminById(AuthjConstants.ROOT_ID);

        if (admin == null) {

            admin = new Admin();

            String pwd = UUIDGenerator.generateUUIDToken();

            admin.setId(AuthjConstants.ROOT_ID);
            admin.setUsername(AuthjConstants.ROOT_INIT_USERNAME);
            admin.setSalt(SaltGenerator.generateUUIDSalt());
            admin.setPassword(PasswordEncoder.toMD5(pwd, admin.getSalt()));
            admin.setNickName(AuthjConstants.ROOT_INIT_NICKNAME);
            admin.setStatus(AdminStatusEnum.NORMAL.getCode());
            admin.setPhone(null);
            admin.setRemark(null);

            adminMapper.insertSelective(admin);

            AdminExample example = new AdminExample();
            example.createCriteria().andUsernameEqualTo(AuthjConstants.ROOT_INIT_USERNAME);
            Admin upId = new Admin();
            upId.setId(AuthjConstants.ROOT_ID);
            adminMapper.updateByExampleSelective(upId, example);

            log.info("自动初始化root用户 -> pwd: {}", pwd);
        }


    }
}
