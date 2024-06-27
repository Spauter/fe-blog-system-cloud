package com.bloducspauter.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(GateWayApp.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
        System.out.println();
    }
}
