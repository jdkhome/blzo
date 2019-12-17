package com.jdkhome.blzo.manage.controller.mine;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.enums.AuthjResponseError;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.AdminService;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.utils.coder.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author link.ji
 * createTime 下午2:49 2018/12/4
 * 个人设置
 */
@Slf4j
@RestController
@RequestMapping("/api/manage/mine/setting")
public class SettingApiController {

    @Autowired
    AuthjManager authjManager;

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    AdminService adminService;

    /**
     * 修改个人信息
     *
     * @return
     */
    @Authj(common = true)
    @Api("修改个人信息")
    @RequestMapping(value = "/basic", method = RequestMethod.POST)
    public ApiResponse apiManagerMineSettingBasic(
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "remark", required = false) String remark
    ) {
        adminBasicService.editAdmin(authjManager.getUserId(), null, null, nickName,
                phone, null, null, remark, null);

        return ApiResponse.success();
    }

    /**
     * 修改密码
     *
     * @return
     */
    @Authj(common = true)
    @Api("修改密码")
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ApiResponse apiManagerMineSettingPassword(
            @RequestParam(value = "oldPwd", required = true) String oldPwd,
            @RequestParam(value = "newPwd", required = true) String newPwd
    ) {

        Integer adminId = authjManager.getUserId();
        // 检查老密码
        Admin admin = adminBasicService.getAdminById(adminId);
        if (!admin.getPassword().equals(PasswordEncoder.toMD5(oldPwd, admin.getSalt()))) {
            log.error("修改密码->老密码错误");
            throw new ServiceException(AuthjResponseError.PASSWORD_ERROR);
        }

        adminBasicService.editAdmin(adminId, null, newPwd, null,
                null, null, null, null, null);

        return ApiResponse.success();
    }

    /**
     * 设置google验证码
     *
     * @return
     */
    @Authj(common = true)
    @Api("设置google验证码")
    @RequestMapping(value = "/google_auth", method = RequestMethod.POST)
    public ApiResponse apiManagerMineSettingGoogleAuth(
            @RequestParam(value = "secret", required = true) String secret,
            @RequestParam(value = "code", required = true) String code
    ) {

        Integer adminId = authjManager.getUserId();
        adminService.setGoogleAuth(adminId, secret, code);
        return ApiResponse.success();
    }

    /**
     * 移除google验证码
     *
     * @return
     */
    @Authj(common = true)
    @Api("移除google验证码")
    @RequestMapping(value = "/google_auth/remove", method = RequestMethod.POST)
    public ApiResponse apiManagerMineSettingGoogleAuthRemove() {

        Integer adminId = authjManager.getUserId();
        adminService.removeGoogleAuth(adminId);
        return ApiResponse.success();
    }

}
