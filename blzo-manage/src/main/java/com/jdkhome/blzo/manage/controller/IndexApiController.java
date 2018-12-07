package com.jdkhome.blzo.manage.controller;

import com.jdkhome.blzo.common.aop.log.controller.Api;
import com.jdkhome.blzo.common.pojo.ApiResponse;
import com.jdkhome.blzo.common.tools.IpTools;
import com.jdkhome.blzo.manage.common.aop.authj.AuthjManager;
import com.jdkhome.blzo.manage.generator.model.Admin;
import com.jdkhome.blzo.manage.service.manage.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jdk on 17/12/6.
 */
@Slf4j
@RestController
public class IndexApiController {


    @Autowired
    AdminService adminService;

    @Autowired
    AuthjManager authjManager;


    /**
     * 登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/api/manage/login", method = RequestMethod.POST)
    @Api("登录")
    public ApiResponse apiManagerSystemAuthAdd(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(value = "username", required = true) String username,
                                               @RequestParam(value = "password", required = true) String password
    ) {

        //登录鉴权
        Admin admin = adminService.login(username, password, IpTools.getIp(request));

        //登录授权
        authjManager.grant(admin.getId());


        return ApiResponse.success();

    }


}
