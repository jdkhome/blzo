package com.jdkhome.blzo.common.configuration.body_reader;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author link.ji
 * createTime 下午6:12 2018/12/4
 */
@Configuration
public class BodyReaderConfiguration {

    @Bean
    public FilterRegistrationBean<BodyReaderFilter> Filters() {
        FilterRegistrationBean<BodyReaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BodyReaderFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setName("bodyReaderFilter");
        return registrationBean;
    }
}
