package com.jdkhome.blzo.ex.consul.configration;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsulConfiguration {
    @Bean
    public ApplicationListener<ApplicationReadyEvent> consulServiceRegistryRegister(ConsulAutoServiceRegistration consulAutoServiceRegistration) {
        return event -> {
            consulAutoServiceRegistration.start();
        };
    }
}