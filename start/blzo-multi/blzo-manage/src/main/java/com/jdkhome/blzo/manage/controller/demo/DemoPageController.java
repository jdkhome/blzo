package com.jdkhome.blzo.manage.controller.demo;

import com.jdkhome.blzo.ex.authj.core.Authj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author link.ji
 * create at 2019-07-27 11:20
 */
@Controller
@RequestMapping("/manage/demo")
public class DemoPageController {


    /**
     * - @Authj 注解目前有4个属性，详细可以前往Authj类查看说明，此处menu=true 代表开发者设置该权限实体可以作为菜单项
     * @return
     */
    @Authj(value = "demo页面", menu = true)
    @RequestMapping("/my_page")
    public String groupAuth() {

        // 对应thymeleaf 的模板文件为 "resources/templates/{你返回的字符串}.html"
        return "manage/demo";
    }

}
