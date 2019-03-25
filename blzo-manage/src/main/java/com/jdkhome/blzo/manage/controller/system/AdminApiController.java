package com.jdkhome.blzo.manage.controller.system;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.enums.AdminStatusEnum;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.AdminService;
import com.jdkhome.blzo.ex.authj.validator.OrganizeValidator;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/api/manage/system/admin")
public class AdminApiController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    AuthjManager authjManager;

    @Autowired
    OrganizeValidator organizeValidator;

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
                                                @RequestParam(value = "organizeId", required = false) Integer organizeId,
                                                @RequestParam(value = "username", required = true) String username,
                                                @RequestParam(value = "password", required = true) String password,
                                                @RequestParam(value = "nickName", required = true) String nickName,
                                                @RequestParam(value = "phone", required = true) String phone,
                                                @RequestParam(value = "email", required = true) String email,
                                                @RequestParam(value = "remark", required = false) String remark
    ) {
        // 非0号组织则只能看自己的数据
        if (0 != authjManager.getOrganizeId()) {
            organizeId = authjManager.getOrganizeId();
        }

        adminBasicService.addAdmin(organizeId, username, password, nickName, phone, email, remark);
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

        if (!organizeValidator.validAdmin(adminId)) {
            log.error("删除管理员 -> 组织鉴权不通过");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        adminBasicService.delAdmin(adminId);
        return ApiResponse.success();
    }

    /**
     * 编辑管理员(该接口不适用于转组织)
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
                                                 @RequestParam(value = "email", required = false) String email,
                                                 @RequestParam(value = "status", required = false) Integer status,
                                                 @RequestParam(value = "remark", required = false) String remark
    ) {

        if (!organizeValidator.validAdmin(adminId)) {
            log.error("编辑管理员 -> 组织鉴权不通过");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        adminBasicService.editAdmin(adminId, username, password, nickName,
                phone, email, AdminStatusEnum.getByCode(status), remark, null);

        return ApiResponse.success();

    }

    @RequestMapping(value = "/change_org", method = RequestMethod.POST)
    @Authj
    @Api("修改管理员组织")
    public ApiResponse apiManagerSystemAdminChangeOrg(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(value = "adminId", required = true) Integer adminId,
                                                      @RequestParam(value = "organizeId", required = true) Integer organizeId
    ) {

        // 只能总组织的管理员有权限修改
        if (0 != authjManager.getOrganizeId()) {
            log.error("修改管理员组织 -> 组织鉴权不通过");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        adminService.changeAdminOrg(adminId, organizeId);

        return ApiResponse.success();

    }


}
