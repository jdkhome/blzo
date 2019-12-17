package com.jdkhome.blzo.manage;

import com.jdkhome.blzo.ex.authj.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Slf4j
@EnableFeignClients(basePackages = {"com.jdkhome.blzo.common.rpc"}) //用于简化客户端RPC 如果不调用其他RPC服务，可以不用开
@ComponentScan(basePackages = {"com.jdkhome.blzo.ex", "com.jdkhome.blzo"})
@EnableTransactionManagement // 开启事务
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
         * 仅当没有0号用户时才会创建成功
         * todo 这里会再优化
         */
        adminService.initRoot();

        log.info("spring start over.");
    }
}
