package com.jdkhome.blzo.friend;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableDiscoveryClient // 使应用程序成为Consul“服务”（即注册自己）和“客户端”（即可以查询Consul查找其他服务）。
@EnableFeignClients(basePackages = {"com.jdkhome.blzo.common.rpc"}) //用于简化客户端RPC 如果不调用其他RPC服务，可以不用开
@EnableTransactionManagement // 开启事务
@MapperScan({"com.jdkhome.blzo.friend.generator.dao"})
@ComponentScan(basePackages = {"com.jdkhome.blzo.ex", "com.jdkhome.blzo"}) // 扫描所有
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FriendApplication implements CommandLineRunner {

    /**
     * Springboot应用程序入口
     */
    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class, args);
    }

    @Override
    public void run(String... args) {

        log.info("Spring初始化over.");
    }
}
