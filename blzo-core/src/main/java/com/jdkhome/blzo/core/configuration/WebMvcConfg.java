package com.jdkhome.blzo.core.configuration;

import com.jdkhome.blzo.ex.version.VersionInterceptor;
import com.jdkhome.blzo.ex.version.VersionMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by jdk on 17/9/5.
 * spring拦截配置，添加自定义拦截组件实现切面功能
 */
@Configuration
public class WebMvcConfg implements WebMvcConfigurer {

    @Autowired
    VersionInterceptor versionInterceptor;

    @Autowired
    VersionMethodArgumentResolver versionMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加一个拦截器
        registry.addInterceptor(versionInterceptor);
    }

    /**
     * 添加参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(versionMethodArgumentResolver);
    }




}


