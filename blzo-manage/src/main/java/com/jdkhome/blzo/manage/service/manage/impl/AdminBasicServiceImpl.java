package com.jdkhome.blzo.manage.service.manage.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.common.component.coder.PasswordEncoder;
import com.jdkhome.blzo.common.component.generator.SaltGenerator;
import com.jdkhome.blzo.common.constants.SqlTemplate;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.exception.ServiceException;
import com.jdkhome.blzo.common.tools.gson.PerfectGson;
import com.jdkhome.blzo.manage.common.aop.authj.AuthjConstants;
import com.jdkhome.blzo.manage.common.aop.authj.menu.LayerDTO;
import com.jdkhome.blzo.manage.common.enums.ResponseError;
import com.jdkhome.blzo.manage.generator.dao.AdminMapper;
import com.jdkhome.blzo.manage.generator.model.Admin;
import com.jdkhome.blzo.manage.generator.model.AdminExample;
import com.jdkhome.blzo.manage.service.manage.AdminBasicService;
import com.jdkhome.blzo.manage.service.manage.GroupBasicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by jdk on 17/11/16.
 * 管理员管理业务实现类
 */
@Slf4j
@Service
public class AdminBasicServiceImpl implements AdminBasicService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    GroupBasicService groupBasicService;

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
    @Override
    public Integer addAdmin(String username, String password, String nickName, String phone, String remark) {

        //入参验证
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(phone)) {
            log.error("添加管理员->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setSalt(SaltGenerator.generateUUIDSalt());
        admin.setPassword(PasswordEncoder.toMD5(password, admin.getSalt()));
        admin.setNickName(nickName);
        admin.setPhone(phone);
        admin.setRemark(remark);

        return adminMapper.insertSelective(admin);
    }

    /**
     * 修改管理员
     *
     * @param adminId
     * @param username 登录名
     * @param password 密码
     * @param nickName
     * @param phone
     * @param remark
     * @return
     */
    @Override
    public Integer editAdmin(Integer adminId, String username, String password, String nickName, String phone, Integer status, String remark, List<LayerDTO> layer) {

        //入参验证
        if (adminId == null) {
            log.error("修改管理员->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        //获取管理员
        Admin admin = this.getAdminById(adminId);

        if (admin == null) {
            log.error("修改管理员->管理员 adminId={} 不存在", adminId);
            throw new ServiceException(ResponseError.RESP_ERROR_ADMIN_NOT_EXIST);
        }

        String salt = admin.getSalt();

        admin = new Admin();

        admin.setId(adminId);

        if (StringUtils.isNotEmpty(username)) {
            admin.setUsername(username);
        }

        if (StringUtils.isNotEmpty(password)) {
            admin.setPassword(PasswordEncoder.toMD5(password, salt));
        }

        if (StringUtils.isNotEmpty(nickName)) {
            admin.setNickName(nickName);
        }

        if (StringUtils.isNotEmpty(phone)) {
            admin.setPhone(phone);
        }

        if (status != null) {
            admin.setStatus(status);
        }

        if (StringUtils.isNotEmpty(remark)) {
            admin.setRemark(remark);
        }

        if (layer != null) {
            // size=0 = > []
            admin.setLayer(PerfectGson.getGson().toJson(layer));
        }

        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer delAdmin(Integer adminId) {

        if (adminId == null) {
            log.error("删除管理员->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        if (adminId.equals(AuthjConstants.ROOT_ID)) {
            log.error("删除管理员->禁止删除root用户");
            throw new ServiceException(CommonResponseError.NO_PERMISSION);
        }

        // 被删除的管理员 退出所有加入的组
        groupBasicService.delGroupAdminByAdminId(adminId);

        // 删除所有该管理员创建的组
        groupBasicService.delGroupByCreateAdminId(adminId);

        return adminMapper.deleteByPrimaryKey(adminId);
    }

    /**
     * 获取管理员通过Id
     *
     * @param adminId
     * @return
     */
    @Override
    public Admin getAdminById(Integer adminId) {

        //入参验证
        if (adminId == null) {
            log.error("获取管理员通过Id->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 获取管理员通过username
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {

        if (StringUtils.isEmpty(username)) {
            log.error("获取管理员通过username->参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        AdminExample example = new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<Admin> objs = adminMapper.selectByExample(example);
        if (objs != null && !objs.isEmpty()) {
            return objs.get(0);
        }
        return null;
    }


    private AdminExample getExample(String username, String nickName, String phone) {

        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }

        if (StringUtils.isNotEmpty(nickName)) {
            criteria.andNickNameLike("%" + nickName + "%");
        }

        if (StringUtils.isNotEmpty(phone)) {
            criteria.andPhoneLike("%" + phone + "%");
        }

        example.setOrderByClause(SqlTemplate.ORDER_BY_ID_DESC);

        return example;
    }

    /**
     * 分页查询管理员
     *
     * @param username
     * @param nickName
     * @param phone
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Admin> getAdminsWithPage(String username, String nickName, String phone, Integer page, Integer size) {

        //入参验证
        if (page == null || size == null) {
            log.error("分页查询管理员->分页参数错误");
            throw new ServiceException(CommonResponseError.PARAMETER_ERROR);
        }

        AdminExample example = this.getExample(username, nickName, phone);


        PageHelper.startPage(page, size);
        PageInfo pageInfo = new PageInfo<>(adminMapper.selectByExample(example));


        return pageInfo;
    }

    /**
     * 获取所有管理员
     *
     * @param username
     * @param nickName
     * @param phone
     * @return
     */
    @Override
    public List<Admin> getAllAdmin(String username, String nickName, String phone) {

        AdminExample example = this.getExample(username, nickName, phone);

        return adminMapper.selectByExample(example);
    }
}
