package com.jdkhome.blzo.manage.controller.system;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.enums.AdminStatusEnum;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.generator.model.Organize;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.OrganizeBasicService;
import com.jdkhome.blzo.ex.authj.validator.OrganizeValidator;
import com.jdkhome.blzo.ex.basic.pojo.PageRequest;
import com.jdkhome.blzo.ex.basic.tools.IpTools;
import com.jdkhome.blzo.ex.ip2region.ip_tool.IpRegionTools;
import com.jdkhome.blzo.manage.pojo.vo.system.AdminVO;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Controller
@RequestMapping("/manage/system/admin")
public class AdminPageController {

    @Autowired
    OrganizeValidator organizeValidator;

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    AuthjManager authjManager;

    @Autowired
    OrganizeBasicService organizeBasicService;

    /**
     * 管理员列表
     * todo 修改组织
     */
    @RequestMapping("/list")
    @Authj(value = "管理员列表", menu = true)
    public String adminList(Model model, HttpServletRequest request,
                            PageRequest pageRequest,
                            @RequestParam(value = "organizeId", required = false) Integer organizeId,
                            @RequestParam(value = "userName", required = false) String userName,
                            @RequestParam(value = "nickName", required = false) String nickName,
                            @RequestParam(value = "phone", required = false) String phone
    ) {

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        // 非0号组织则只能看自己的数据
        if (0 != authjManager.getOrganizeId()) {
            organizeId = authjManager.getOrganizeId();
        }

        //获取管理员列表
        PageInfo pageInfo = adminBasicService.getAdminsWithPage(organizeId, userName, nickName, phone, null, pageRequest.getPage(), pageRequest.getSize());
        List<Admin> admins = pageInfo.getList();

        List<AdminVO> vos = new ArrayList<>(admins.size());
        admins.forEach(admin -> {

            AdminVO vo = new AdminVO();
            BeanUtils.copyProperties(admin, vo);
            AdminStatusEnum statusEnum = AdminStatusEnum.getByCode(admin.getStatus());

            vo.setStatusStr(statusEnum.getName());
            vo.setIpCity(IpRegionTools.getCity(IpTools.getIp(request)));

            vos.add(vo);

        });
        pageInfo.setList(vos);

        AdminStatusEnum[] adminStatusEnums = AdminStatusEnum.values();


        List<Organize> organizes = organizeBasicService.getAllOrganize(null, null, null, null);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("adminStatusEnums", adminStatusEnums);
        model.addAttribute("organizes", organizes);

        return "manage/page/system/admin/list";
    }

}
