package com.jdkhome.blzo.manage.configuration;

import com.jdkhome.blzo.ex.basic.aop.api.ApiBeforeCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by jdk on 2019/1/7.
 */
@Slf4j
@Configuration
public class MyApiBeforeConfig {

    @Primary
    @Bean
    ApiBeforeCustom myApiBeforeCustom() {
        return joinPoint -> log.info("[apiBeforeCustom] -> nothing todo hehe.");
    }

}
