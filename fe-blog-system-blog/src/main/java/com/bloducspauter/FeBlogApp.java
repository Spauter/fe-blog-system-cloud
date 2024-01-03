package com.bloducspauter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession
@SpringBootApplication
@EnableDiscoveryClient //启动服务注册发现功能
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
public class FeBlogApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FeBlogApp.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);

    }
}
