package com.jdkhome.blzo.manage.controller;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by jdk on 17/6/30.
 * 主页控制器
 */
@Slf4j
@Controller
public class IndexPageController {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 登录页
     */
    @RequestMapping("/manage/login")
    @Authj(value = "登录", auth = false)
    public String login(Model model, HttpServletRequest request) {

        //访问登录页自动退出登录
        authjManager.delGrant();
        model.addAttribute("nickName", "未登录");
        model.addAttribute("login", false);
        return "manage/login";
    }


    /**
     * 主页，，随便什么吧主页 wellcome
     */
    @RequestMapping({"/manage/index", "/", "/index"})
    @Authj(value = "主页", common = true)
    public String index(Model model, HttpServletRequest request) {

        Admin admin = adminBasicService.getAdminById(authjManager.getUserId());
        model.addAttribute("obj", admin);

        return "manage/index";
    }


}
