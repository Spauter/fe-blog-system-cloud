package com.bloducspauter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
@EnableRedisHttpSession
public class FeBlogOrnamentApp {
    public static void main(String[] args) {
        SpringApplication.run(FeBlogOrnamentApp.class);
    }

}
