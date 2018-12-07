package com.jdkhome.blzo.manage.controller.system;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.common.pojo.PageRequest;
import com.jdkhome.blzo.common.tools.IpTools;
import com.jdkhome.blzo.manage.common.aop.authj.Authj;
import com.jdkhome.blzo.manage.common.enums.AdminStatusEnum;
import com.jdkhome.blzo.manage.generator.model.Admin;
import com.jdkhome.blzo.manage.pojo.vo.system.AdminVO;
import com.jdkhome.blzo.manage.service.manage.AdminBasicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdk on 17/11/28.
 * 后台菜单管理
 * 管理员
 */
@Controller
@RequestMapping("/manage/system/admin")
public class AdminPageController {


    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 管理员列表
     */
    @RequestMapping("/list")
    @Authj(value = "管理员列表", menu = true)
    public String adminList(Model model, HttpServletRequest request,
                            PageRequest pageRequest,
                            @RequestParam(value = "userName", required = false) String userName,
                            @RequestParam(value = "nickName", required = false) String nickName,
                            @RequestParam(value = "phone", required = false) String phone
    ) {

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        //获取管理员列表
        PageInfo pageInfo = adminBasicService.getAdminsWithPage(userName, nickName, phone, pageRequest.getPage(), pageRequest.getSize());
        List<Admin> admins = pageInfo.getList();

        List<AdminVO> vos = new ArrayList<>(admins.size());
        admins.forEach(admin -> {

            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(admin, vo);
            AdminStatusEnum statusEnum = AdminStatusEnum.getByCode(admin.getStatus());

            vo.setStatusStr(statusEnum.getName());
            vo.setIpCity(IpTools.getCity(IpTools.getIp(request)));

            vos.add(vo);

        });
        pageInfo.setList(vos);

        AdminStatusEnum[] adminStatusEnums = AdminStatusEnum.values();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("adminStatusEnums", adminStatusEnums);
        return "manage/page/system/admin/list";
    }

    /**
     * 管理员密码重置
     */
    @RequestMapping("/edit")
    @Authj("管理员编辑页")
    public String adminEdit(Model model, HttpServletRequest request,
                            @RequestParam(value = "adminId", required = true) Integer adminId) {

        Admin obj = adminBasicService.getAdminById(adminId);

        model.addAttribute("obj", obj);

        return "manage/page/system/admin/edit";
    }

    /**
     * 创建管理员页面
     */
    @Authj(value = "创建管理员页面", menu = true)
    @RequestMapping("/add")
    public String adminAdd(Model model, HttpServletRequest request) {

        return "manage/page/system/admin/add";
    }

}
