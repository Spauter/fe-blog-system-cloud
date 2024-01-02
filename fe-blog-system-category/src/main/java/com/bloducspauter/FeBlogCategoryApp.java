package com.bloducspauter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
@MapperScan("com.bloducspauter.mapper")
public class FeBlogCategoryApp {
    public static void main(String[] args) {
        SpringApplication.run(FeBlogCategoryApp.class);
    }
}
