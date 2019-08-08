package com.jdkhome.blzo.manage;

import com.jdkhome.blzo.ex.authj.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
@ComponentScan(basePackages = {"com.jdkhome.blzo.ex", "com.jdkhome.blzo"})
@EnableScheduling
@EnableTransactionManagement
@SpringBootApplication
public class ManageApplication implements CommandLineRunner {

    @Autowired
    AdminService adminService;

    /**
     * Springboot应用程序入口
     */
    public static void main(String[] args) {
          SpringApplication.run(ManageApplication.class, args);
    }

    @Override
    public void run(String... args){
        /**
         * 初始化Root用户
         */
        adminService.initRoot();
        log.info("over.");
    }
}
