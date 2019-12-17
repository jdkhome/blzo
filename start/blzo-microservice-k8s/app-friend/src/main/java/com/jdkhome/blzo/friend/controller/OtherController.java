package com.jdkhome.blzo.friend.controller;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author link.ji
 * createTime 下午6:55 2018/6/19
 */
@RestController
public class OtherController {
    /**
     * 测试
     *
     * @return
     */
    @Api(value = "健康检查", log = false)
    @RequestMapping(value = "/api/health")
    public ApiResponse health() {
        return ApiResponse.success();
    }

}
