package com.jdkhome.blzo.core;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@MapperScan({"com.jdkhome.blzo.common.generator.dao"})
@ComponentScan(basePackages = {"com.jdkhome.blzo.ex", "com.jdkhome.blzo"}) // 当你修改了项目包名, 记得修改这里
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
        // 这里可以放你希望程序启动后自动执行的方法
        log.info("spring init over.");
    }
}
