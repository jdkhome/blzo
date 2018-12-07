package com.jdkhome.blzo.manage;

import com.jdkhome.blzo.manage.service.manage.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"com.jdkhome.blzo"})
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
    public void run(String... args) throws Exception {
        /**
         * 初始化Root用户
         */
        adminService.initRoot();
    }
}
