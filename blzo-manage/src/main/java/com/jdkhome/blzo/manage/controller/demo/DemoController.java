package com.jdkhome.blzo.manage.controller.demo;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * author link.ji
 * createTime 下午3:17 2018/12/5
 * Demo
 */
@Slf4j
@Controller
public class DemoController {


    /**
     * 代表这个接口 的名称是 "demo接口1" 它需要鉴权 它不能作为菜单 它不属于公共权限
     * @return
     */
    @Authj(value = "demo接口1", auth = true, menu = false, common = false)
    @ResponseBody
    @RequestMapping(value = "/api/manage/demo/api_1", method = RequestMethod.POST)
    public ApiResponse apiManagerDemoApi1() {
        return ApiResponse.success();
    }

    /**
     * 代表这个页面 的名称是 "demo页面1" 它需要鉴权 它可以作为菜单 它不属于公共权限
     * @return
     */
    @Authj(value = "demo页面1", auth = true, menu = true, common = false)
    @RequestMapping("/manage/demo/page_1")
    public String groupAuth() {
        return "manage/demo";
    }

}
