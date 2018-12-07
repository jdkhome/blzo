package com.jdkhome.blzo.manage.controller.mine;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.common.enums.CommonResponseError;
import com.jdkhome.blzo.common.exception.ServiceException;
import com.jdkhome.blzo.common.pojo.PageRequest;
import com.jdkhome.blzo.manage.common.aop.authj.*;
import com.jdkhome.blzo.manage.generator.model.Admin;
import com.jdkhome.blzo.manage.generator.model.Group;
import com.jdkhome.blzo.manage.generator.model.GroupAdmin;
import com.jdkhome.blzo.manage.generator.model.GroupAuth;
import com.jdkhome.blzo.manage.pojo.vo.group.GroupVO;
import com.jdkhome.blzo.manage.pojo.vo.mine.GroupAdminVO;
import com.jdkhome.blzo.manage.pojo.vo.mine.GroupAuthVO;
import com.jdkhome.blzo.manage.service.manage.AdminBasicService;
import com.jdkhome.blzo.manage.service.manage.GroupBasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jdk on 17/11/28.
 * 工作组页面控制器
 */
@Slf4j
@Controller
@RequestMapping("/manage/mine/group")
public class GroupPageController {


    @Autowired
    GroupBasicService groupBasicService;

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    AuthjCache authjCache;

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AuthjService authjService;

    /**
     * 我的权限
     */
    @Authj(value = "我的权限", common = true, menu = true)
    @RequestMapping("/dashboard")
    public String dashboard(Model model) {

        // 获取加入的小组

        List<GroupAdmin> groupAdmins = groupBasicService.getGroupAdminByAdminId(authjManager.getUserId());
        List<GroupVO> addGroups = new ArrayList<>(groupAdmins.size());
        groupAdmins.forEach(groupAdmin -> {
            Group group = groupBasicService.getGroupById(groupAdmin.getGroupId());
            Admin createAdmin = adminBasicService.getAdminById(group.getCreateAdminId());

            List<GroupAuth> groupAuths = groupBasicService.getGroupAuthByGroupId(group.getId());

            List<AuthjBean> auths = new ArrayList<>();
            groupAuths.stream()
                    .filter(groupAuth -> authjCache.hasAuthj(groupAuth.getUri()))
                    .forEach(groupAuth -> auths.add(authjCache.getAuthj(groupAuth.getUri())));

            GroupVO vo = new GroupVO();
            vo.setId(group.getId());
            vo.setName(group.getName());
            vo.setCreateAdminName(createAdmin.getNickName());
            vo.setAuths(auths);

            addGroups.add(vo);

        });

        model.addAttribute("addGroups", addGroups);


        // 获取我创建的小组
        List<Group> myGroups = groupBasicService.getAllGroup(null, authjManager.getUserId());

        model.addAttribute("myGroups", myGroups);

        return "manage/page/mine/group/dashboard";
    }

    /**
     * 组成员页
     */
    @Authj("组成员页")
    @RequestMapping("/admin")
    public String groupAdmin(Model model, HttpServletRequest request,
                             PageRequest pageRequest,
                             @RequestParam(value = "groupId", required = true) Integer groupId) {

        Group group = groupBasicService.getGroupById(groupId);

        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("组成员页 -> 当前用户不是改组的创建者");
            throw new ServiceException(CommonResponseError.NO_PERMISSION);
        }

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        //组信息
        model.addAttribute("group", group);

        //获取所有管理员
        PageInfo pageInfo = adminBasicService.getAdminsWithPage(null, null, null, pageRequest.getPage(), pageRequest.getSize());

        //获取组内所有管理员Id

        Set<Integer> adminSet = new HashSet<>();
        groupBasicService.getGroupAdminByGroupId(groupId).stream().forEach(groupAdmin -> adminSet.add(groupAdmin.getAdminId()));

        List<GroupAdminVO> list = new ArrayList<>(pageInfo.getSize());
        List<Admin> adminList = pageInfo.getList();
        adminList.stream().forEach(admin -> {
            GroupAdminVO groupAdminVO = new GroupAdminVO();
            BeanUtils.copyProperties(admin, groupAdminVO);

            //查看是否有关联
            groupAdminVO.setHave(adminSet.contains(admin.getId()));
            list.add(groupAdminVO);
        });
        pageInfo.setList(list);

        //组信息
        model.addAttribute("pageInfo", pageInfo);

        return "manage/page/mine/group/admin";
    }

    /**
     * 组权限页
     */
    @Authj("组权限页")
    @RequestMapping("/auth")
    public String groupAuth(Model model, HttpServletRequest request,
                            PageRequest pageRequest,
                            @RequestParam(value = "groupId", required = true) Integer groupId) {

        Group group = groupBasicService.getGroupById(groupId);

        if (!group.getCreateAdminId().equals(authjManager.getUserId())) {
            log.error("组权限页 -> 当前用户不是改组的创建者");
            throw new ServiceException(CommonResponseError.NO_PERMISSION);
        }

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        // 组拥有的权限列表auth
        List<GroupAuth> groupAuths = groupBasicService.getGroupAuthByGroupId(groupId);

        // 当前用户拥有的所有不是common 的 authjBeans都是可选权限
        Set<String> allAuth = authjService.getAdminAuth(authjManager.getUserId());
        List<AuthjBean> authjBeans = new ArrayList<>();
        allAuth.stream()
                .filter(uri -> authjCache.hasAuthj(uri) && !authjCache.getAuthj(uri).getCommon())
                .forEach(uri -> authjBeans.add(authjCache.getAuthj(uri)));


        // 这是组拥有的权限的uri的集合
        Set<String> uris = new HashSet<>(groupAuths.size());

        List<GroupAuthVO> groupAuthVOList = new ArrayList<>(authjBeans.size());

        groupAuths.stream().forEachOrdered(groupAuth -> {
            GroupAuthVO groupAuthVO = new GroupAuthVO();
            groupAuthVO.setUri(groupAuth.getUri());
            groupAuthVO.setHave(true);
            groupAuthVO.setAuthjBean(authjCache.getAuthj(groupAuth.getUri()));
            groupAuthVOList.add(groupAuthVO);

            uris.add(groupAuth.getUri());
        });

        authjBeans.stream().filter(authjBean -> !uris.contains(authjBean.getUri())).forEachOrdered(authjBean -> {
            GroupAuthVO groupAuthVO = new GroupAuthVO();
            groupAuthVO.setUri(authjBean.getUri());
            groupAuthVO.setHave(false);
            groupAuthVO.setAuthjBean(authjBean);
            groupAuthVOList.add(groupAuthVO);
        });

        //组信息
        model.addAttribute("group", group);
        // 列表
        model.addAttribute("groupAuthVOList", groupAuthVOList);

        return "manage/page/mine/group/auth";
    }

}
