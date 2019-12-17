package com.jdkhome.blzo.manage.controller.demo;

import com.jdkhome.blzo.ex.authj.core.Authj;
import com.jdkhome.blzo.ex.basic.aop.api.Api;
import com.jdkhome.blzo.ex.basic.pojo.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * author linkji.
 * create at 2019-07-27 11:20
 */
@RestController
@RequestMapping("/api/manage/demo")
public class DemoApiController {

    /**
     * * @Api 注解来自于 blzo-ex-basic 增加了该注解的方法在被请求时会自动打印日志
     * * @Authj 注解来自于 blzo-ex-authj 加上了该注解的方法，在项目启动时会被扫描为权限实体，权限实体的名称可以由@Authj注解指定，也可以从@Api注解获取
     * @return
     */
    @Authj
    @Api("demo接口")
    @RequestMapping(value = "/my_api", method = RequestMethod.POST)
    public ApiResponse apiManagerDemoMyApi() {

        // 十分建议用ApiResponse结构来封装你的响应对象 详细使用请参考ApiResponse类
        return ApiResponse.success();
    }

}
