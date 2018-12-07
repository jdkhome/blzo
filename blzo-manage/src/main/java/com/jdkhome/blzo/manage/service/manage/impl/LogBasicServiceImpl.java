package com.jdkhome.blzo.manage.service.manage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.common.constants.SqlTemplate;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.exception.ServiceException;
import com.jdkhome.blzo.manage.generator.dao.LogMapper;
import com.jdkhome.blzo.manage.generator.model.Admin;
import com.jdkhome.blzo.manage.generator.model.Log;
import com.jdkhome.blzo.manage.generator.model.LogExample;
import com.jdkhome.blzo.manage.service.manage.AdminBasicService;
import com.jdkhome.blzo.manage.service.manage.LogBasicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author linkji.
 */
@Slf4j
@Service
public class LogBasicServiceImpl implements LogBasicService {

    @Autowired
    LogMapper logMapper;

    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 添加日志
     *
     * @param adminId   管理员Id
     * @param authjUri  authj地址
     * @param authjName authjName
     * @param paramers  参数(json)
     * @param ip        ip
     * @return
     */
    @Override
    public Integer addLog(Integer adminId, String adminName, String authjUri, String authjName, String paramers, String ip) {

        if (adminId == null || StringUtils.isEmpty(adminName) || StringUtils.isEmpty(authjUri) || StringUtils.isEmpty(authjName)) {
            log.error("添加日志 -> 参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        Log log = new Log();
        log.setAdminId(adminId);
        log.setAdminName(adminName);
        log.setAuthjUri(authjUri);
        log.setAuthjName(authjName);
        log.setParamers(paramers);
        log.setIp(ip);

        logMapper.insertSelective(log);

        return 0;
    }

    /**
     * 获取日志通过Id
     *
     * @param logId
     * @return
     */
    @Override
    public Log getLog(Integer logId) {

        if (logId == null) {
            log.error("获取日志通过Id -> 参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        Log log = logMapper.selectByPrimaryKey(logId);
        return log;
    }

    private LogExample getExample(String nickName, String authjUri, String authjName) {

        LogExample example = new LogExample();
        LogExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(nickName)) {
            List<Admin> adminList = adminBasicService.getAllAdmin(null, nickName, null);
            List<Integer> adminIds = new ArrayList<>();
            adminIds.add(-1);
            if (adminList != null && !adminList.isEmpty()) {
                adminList.forEach(admin -> adminIds.add(admin.getId()));
            }

            criteria.andAdminIdIn(adminIds);
        }

        if (StringUtils.isNotEmpty(authjUri)) {
            criteria.andAuthjUriLike("%" + authjUri + "%");
        }

        if (StringUtils.isNotEmpty(authjName)) {
            criteria.andAuthjNameLike("%" + authjName + "%");
        }

        example.setOrderByClause(SqlTemplate.ORDER_BY_ID_DESC);

        return example;

    }

    /**
     * 分页查询日志
     *
     * @param nickName  用户昵称
     * @param authjUri  权限Uri
     * @param authjName 权限Name
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Log> getLogWithPage(String nickName, String authjUri, String authjName, Integer page, Integer size) {

        if (page == null || size == null) {
            log.error("分页查询日志 -> 参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        LogExample example = this.getExample(nickName, authjUri, authjName);

        PageHelper.startPage(page, size);
        return new PageInfo<>(logMapper.selectByExampleWithBLOBs(example));
    }
}
