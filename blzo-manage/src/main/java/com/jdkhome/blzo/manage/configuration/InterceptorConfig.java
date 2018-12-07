package com.jdkhome.blzo.manage.configuration;

import com.jdkhome.blzo.manage.common.aop.authj.AuthjInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lee on 17/9/5.
 * spring拦截配置，添加自定义拦截组件实现切面功能
 *
 * @CreatedBy lee
 * @Date 17/9/5
 */
@Component
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    AuthjInterceptor authjInterceptor;


    /**
     * 添加登录校验拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册管理后台鉴权拦截器
        registry.addInterceptor(authjInterceptor);

        super.addInterceptors(registry);

    }




}


