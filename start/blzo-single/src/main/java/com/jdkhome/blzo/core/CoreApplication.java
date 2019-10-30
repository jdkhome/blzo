package com.jdkhome.blzo.core;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@ComponentScan(basePackages = {"com.jdkhome.blzo.ex", "com.jdkhome.blzo"}) // 扫描所有
@SpringBootApplication
public class CoreApplication implements CommandLineRunner {

    /**
     * Springboot应用程序入口
     */
    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Spring初始化over.");
    }
}
