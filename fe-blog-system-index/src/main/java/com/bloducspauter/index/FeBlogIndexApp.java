package com.bloducspauter.index;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FeBlogIndexApp {
    public static void main(String[] args) {
        SpringApplication.run(FeBlogIndexApp.class);
    }
}
