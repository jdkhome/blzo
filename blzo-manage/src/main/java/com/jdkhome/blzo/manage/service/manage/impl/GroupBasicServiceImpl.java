package com.jdkhome.blzo.manage.service.manage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.common.constants.SqlTemplate;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.exception.ServiceException;
import com.jdkhome.blzo.manage.common.aop.authj.AuthjManager;
import com.jdkhome.blzo.manage.generator.dao.GroupAdminMapper;
import com.jdkhome.blzo.manage.generator.dao.GroupAuthMapper;
import com.jdkhome.blzo.manage.generator.dao.GroupMapper;
import com.jdkhome.blzo.manage.generator.model.*;
import com.jdkhome.blzo.manage.service.manage.GroupBasicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jdk on 18/1/6.
 */
@Slf4j
@Service
public class GroupBasicServiceImpl implements GroupBasicService {

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    GroupAdminMapper groupAdminMapper;

    @Autowired
    GroupAuthMapper groupAuthMapper;

    @Autowired
    AuthjManager authjManager;

    /**
     * 添加组
     *
     * @param name
     * @param remark
     * @return
     */
    @Override
    public Integer addGroup(String name, String remark) {

        //入参验证
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(remark)) {
            log.error("添加组->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        Group group = new Group();
        group.setName(name);
        group.setRemark(remark);
        group.setCreateAdminId(authjManager.getUserId());

        return groupMapper.insertSelective(group);
    }

    /**
     * 增加组管理员关联
     *
     * @param groupId
     * @param adminId
     * @return
     */
    @Override
    public Integer addGroupAdmin(Integer groupId, Integer adminId) {

        //入参验证
        if (groupId == null || adminId == null) {
            log.error("增加组管理员关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setGroupId(groupId);
        groupAdmin.setAdminId(adminId);
        groupAdmin.setCreateAdminId(authjManager.getUserId());

        return groupAdminMapper.insertSelective(groupAdmin);
    }

    /**
     * 增加组权限关联
     *
     * @param groupId
     * @param uri
     * @return
     */
    @Override
    public Integer addGroupAuth(Integer groupId, String uri) {
        //入参验证
        if (groupId == null || StringUtils.isEmpty(uri)) {
            log.error("增加组权限关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAuth groupAuth = new GroupAuth();
        groupAuth.setGroupId(groupId);
        groupAuth.setUri(uri);
        groupAuth.setCreateAdminId(authjManager.getUserId());

        return groupAuthMapper.insertSelective(groupAuth);
    }

    /**
     * 修改组
     *
     * @param groupId
     * @param name
     * @param remark
     * @return
     */
    @Override
    public Integer editGroups(Integer groupId, String name, String remark) {
        if (groupId == null) {
            log.error("修改组->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        Group group = new Group();

        group.setId(groupId);

        if (StringUtils.isNotEmpty(name)) {
            group.setName(name);
        }

        if (StringUtils.isNotEmpty(remark)) {
            group.setRemark(remark);
        }

        return groupMapper.updateByPrimaryKeySelective(group);
    }

    /**
     * 删除组
     *
     * @param groupId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer delGroup(Integer groupId) {
        if (groupId == null) {
            log.error("删除组->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        // 删除这个组的所有关联！
        this.delGroupAuthByGroupId(groupId);
        this.delGroupAdminByGroupId(groupId);

        return groupMapper.deleteByPrimaryKey(groupId);
    }

    /**
     * 删除某管理员创建的所有组
     *
     * @param adminId
     * @return
     */
    @Override
    public Integer delGroupByCreateAdminId(Integer adminId) {

        if (adminId == null) {
            log.error("删除某管理员创建的所有组->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        var list = this.getAllGroup(null, adminId);

        list.forEach(group -> this.delGroup(group.getId()));

        return 0;
    }

    /**
     * 删除组管理员关联
     *
     * @param groupId
     * @param adminId
     * @return
     */
    @Override
    public Integer delGroupAdmin(Integer groupId, Integer adminId) {

        //入参验证
        if (groupId == null || adminId == null) {
            log.error("删除组管理员关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andAdminIdEqualTo(adminId);

        return groupAdminMapper.deleteByExample(example);
    }

    /**
     * 删除某组的所有管理员关联
     *
     * @param groupId
     * @return
     */
    @Override
    public Integer delGroupAdminByGroupId(Integer groupId) {

        if (groupId == null) {
            log.error("删除某组的所有管理员关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return groupAdminMapper.deleteByExample(example);
    }

    /**
     * 删除某管理员的所有组关联
     *
     * @param adminId
     * @return
     */
    @Override
    public Integer delGroupAdminByAdminId(Integer adminId) {
        if (adminId == null) {
            log.error("删除某管理员的所有组关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andAdminIdEqualTo(adminId);

        return groupAdminMapper.deleteByExample(example);
    }

    /**
     * 删除组权限关联
     *
     * @param groupId
     * @param uri
     * @return
     */
    @Override
    public Integer delGroupAuth(Integer groupId, String uri) {

        //入参验证
        if (groupId == null || StringUtils.isEmpty(uri)) {
            log.error("删除组权限关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAuthExample example = new GroupAuthExample();
        example.createCriteria().andGroupIdEqualTo(groupId).andUriEqualTo(uri);

        return groupAuthMapper.deleteByExample(example);
    }

    /**
     * 删除组所有权限关联
     *
     * @param groupId
     * @return
     */
    @Override
    public Integer delGroupAuthByGroupId(Integer groupId) {

        //入参验证
        if (groupId == null) {
            log.error("删除组所有权限关联->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAuthExample example = new GroupAuthExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return groupAuthMapper.deleteByExample(example);
    }

    /**
     * 获取组通过Id
     *
     * @param groupId
     * @return
     */
    @Override
    public Group getGroupById(Integer groupId) {
        return groupMapper.selectByPrimaryKey(groupId);
    }


    public GroupExample getExample(String name, Integer createId) {

        GroupExample example = new GroupExample();
        GroupExample.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (createId != null) {
            criteria.andCreateAdminIdEqualTo(createId);
        }

        example.setOrderByClause(SqlTemplate.ORDER_BY_ID_DESC);

        return example;
    }

    /**
     * 分页查询组
     *
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Group> getGroupsWithPage(String name, Integer createId, Integer page, Integer size) {

        //入参验证
        if (page == null || size == null) {
            log.error("分页查询组->分页参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupExample example = this.getExample(name, createId);
        PageHelper.startPage(page, size);

        return new PageInfo<>(groupMapper.selectByExample(example));
    }

    /**
     * 获取所有组
     *
     * @param name
     * @return
     */
    @Override
    public List<Group> getAllGroup(String name, Integer createId) {
        GroupExample example = this.getExample(name, createId);
        return groupMapper.selectByExample(example);
    }

    /**
     * 获取GroupAdmin 通过管理员Id
     *
     * @param adminId
     * @return
     */
    @Override
    public List<GroupAdmin> getGroupAdminByAdminId(Integer adminId) {

        if (adminId == null) {
            log.error("获取GroupAdmin 通过管理员Id->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andAdminIdEqualTo(adminId);

        return groupAdminMapper.selectByExample(example);
    }

    /**
     * 获取GroupAuth 通过组Id
     *
     * @param groupId
     * @return
     */
    @Override
    public List<GroupAuth> getGroupAuthByGroupId(Integer groupId) {

        if (groupId == null) {
            log.error("获取GroupAuth 通过组Id->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAuthExample example = new GroupAuthExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return groupAuthMapper.selectByExample(example);
    }


    /**
     * 获取GroupAdmin 通过组Id
     *
     * @param groupId
     * @return
     */
    @Override
    public List<GroupAdmin> getGroupAdminByGroupId(Integer groupId) {

        if (groupId == null) {
            log.error("获取GroupAdmin 通过组Id->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return groupAdminMapper.selectByExample(example);
    }
}
