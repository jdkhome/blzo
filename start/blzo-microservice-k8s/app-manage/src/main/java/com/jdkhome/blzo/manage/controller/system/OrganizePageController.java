package com.jdkhome.blzo.manage.controller.system;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.enums.OrganizeStatusEnum;
import com.jdkhome.blzo.ex.authj.service.OrganizeBasicService;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author linkji
 * @date 2019-03-11 14:18
 * 组织管理页面控制器
 */
@Slf4j
@Controller
@RequestMapping("/manage/system/organize")
public class OrganizePageController {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    OrganizeBasicService organizeBasicService;

    /**
     * 组织列表页
     */
    @RequestMapping("/list")
    @Authj(value = "组织列表页", menu = true)
    public String list(Model model, HttpServletRequest request,
                       PageRequest pageRequest,
                       @RequestParam(value = "organizeId", required = false) Integer organizeId,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "status", required = false) Integer status,
                       @RequestParam(value = "remark", required = false) String remark
    ) {

        if (pageRequest == null) {
            pageRequest = new PageRequest();
        }

        // 只有0号组织才可以访问组织管理相关页面
        if (0 != authjManager.getOrganizeId()) {
            log.error("组织列表页 -> 非0号组织，没有权限");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        // 获取组织列表
        PageInfo pageInfo = organizeBasicService.getOrganizeWithPage(organizeId, name,
                OrganizeStatusEnum.getByCode(status), remark, pageRequest.page, pageRequest.size);

        OrganizeStatusEnum[] statusEnums = OrganizeStatusEnum.values();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("statusEnums", statusEnums);

        return "manage/page/system/organize/list";
    }

}
