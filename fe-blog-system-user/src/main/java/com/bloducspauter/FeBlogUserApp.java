package com.bloducspauter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
public class FeBlogUserApp{
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(new Class[]{FeBlogUserApp.class});
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }
}
