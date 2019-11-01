package com.jdkhome.blzo.friend.controller;

import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.friend.service.FriendService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * create by linkji.
 * create at 16:29 2019-10-28
 */
@Slf4j
@RestController
@RequestMapping("/api/friend")
public class FriendApiController {

    @Autowired
    FriendService friendService;

    @Data
    class FriendAddParams {
        @NotBlank(message = "用户名不能为空")
        String name;

        @NotBlank(message = "手机号不能为空")
        String phone;
    }

    @Api("添加朋友")
    @RequestMapping("/add")
    public ApiResponse apiFriendAdd(@Valid FriendAddParams params, BindingResult validResult) {


        friendService.addFriend(params.name, params.phone);
        return ApiResponse.success();
    }
}
