package com.jdkhome.blzo.manage.controller.mine;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author link.ji
 * createTime 下午2:49 2018/12/4
 * 个人设置
 */
@Controller
@RequestMapping("/manage/mine/setting")
public class SettingPageController {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 个人信息设置
     */
    @RequestMapping("/basic")
    @Authj(value = "个人信息设置", common = true)
    public String basic(Model model) {

        Admin admin = adminBasicService.getAdminById(authjManager.getUserId());
        model.addAttribute("obj", admin);

        return "manage/page/mine/setting/basic";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/password")
    @Authj(value = "修改密码", common = true)
    public String password(Model model) {

        return "manage/page/mine/setting/password";
    }

}
