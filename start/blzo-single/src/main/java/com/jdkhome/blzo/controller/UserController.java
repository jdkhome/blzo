package com.jdkhome.blzo.controller;

import com.jdkhome.blzo.common.constants.RegexConstant;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * create by linkji.
 * [示例] 用户业务控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * @api {post} /api/user/add 添加用户
     * @apiName apiUserAdd
     * @apiGroup user
     * @apiDescription 添加用户
     * @apiParam {String} username 用户名
     * @apiParam {String} phone 手机号
     * @apiSuccessExample {json} Success-response:
     * HTTP/1.1 0 OK
     * <p>
     * {
     * "code": 200,
     * "message": "success",
     * "data": {...}
     * }
     * @apiErrorExample {json} Error-response:
     * HTTP/1.1 error
     * <p>
     * {
     * "code":xxx,
     * "message":"xxx"
     * "data":{...}
     * }
     */
    @Data
    static class UserAddParams {
        @NotBlank(message = "用户名不能为空")
        String username;

        @NotBlank(message = "手机号不能为空")
        @Pattern(regexp = RegexConstant.PHONE, message = "手机号格式不正确")
        String phone;
    }

    @Api("添加用户")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResponse apiUserAdd(@Valid UserAddParams params, BindingResult validResult) {

        userService.add(params.username, params.phone);
        return ApiResponse.success();
    }

}
