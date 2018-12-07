package com.jdkhome.blzo.manage.controller.system;

import com.jdkhome.blzo.common.aop.log.controller.Api;
import com.jdkhome.blzo.common.pojo.ApiResponse;
import com.jdkhome.blzo.manage.common.aop.authj.Authj;
import com.jdkhome.blzo.manage.service.manage.AdminBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jdk on 17/12/5.
 */
@RestController
@RequestMapping("/api/manage/mine/admin")
public class AdminApiController {


    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 添加管理员
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Authj
    @Api("添加管理员")
    public ApiResponse apiManagerSystemAdminAdd(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(value = "username", required = true) String username,
                                                @RequestParam(value = "password", required = true) String password,
                                                @RequestParam(value = "nickName", required = true) String nickName,
                                                @RequestParam(value = "phone", required = true) String phone,
                                                @RequestParam(value = "remark", required = false) String remark
    ) {
        adminBasicService.addAdmin(username, password, nickName, phone, remark);
        return ApiResponse.success();

    }

    /**
     * 删除管理员
     *
     * @param request
     * @param response
     * @param adminId
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @Authj
    @Api("删除管理员")
    public ApiResponse apiManagerSystemAdminDel(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(value = "adminId", required = true) Integer adminId
    ) {
        adminBasicService.delAdmin(adminId);
        return ApiResponse.success();
    }

    /**
     * 编辑管理员
     *
     * @param request
     * @param response
     * @param adminId
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @Authj
    @Api("编辑管理员")
    public ApiResponse apiManagerSystemAdminEdit(HttpServletRequest request, HttpServletResponse response,
                                                 @RequestParam(value = "adminId", required = true) Integer adminId,
                                                 @RequestParam(value = "username", required = false) String username,
                                                 @RequestParam(value = "password", required = false) String password,
                                                 @RequestParam(value = "nickName", required = false) String nickName,
                                                 @RequestParam(value = "phone", required = false) String phone,
                                                 @RequestParam(value = "status", required = false) Integer status,
                                                 @RequestParam(value = "remark", required = false) String remark
    ) {

        adminBasicService.editAdmin(adminId, username, password, nickName, phone, status, remark, null);

        return ApiResponse.success();

    }

}
