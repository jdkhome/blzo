package com.jdkhome.blzo.core.controller;

import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import com.jdkhome.blzo.ex.utils.constants.RegularExpression;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * author link.ji
 * createTime 下午6:55 2018/6/19
 */
@RestController
@RequestMapping("/api/test")
public class TestController {


    /**
     * @api {post} /api/test/aaa [测试]测试接口
     * @apiName apiTestAaa
     * @apiGroup TestController
     * @apiDescription 测试接口
     * @apiParam {String} email email地址
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
    class TestParams {
        @NotBlank(message = "邮箱不能为空")
        @Pattern(regexp = RegularExpression.REGEX_EMAIL, message = "请不要搞事")
        String email;
    }

    @Api("测试用接口")
    @RequestMapping(value = "/aaa", method = RequestMethod.POST)
    public ApiResponse apiTestAaa(@Valid TestParams params, BindingResult validResult) {

        return ApiResponse.success();
    }
}
