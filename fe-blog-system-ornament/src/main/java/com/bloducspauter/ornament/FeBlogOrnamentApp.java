package com.bloducspauter.ornament;

import com.bloducspauter.ornament.service.MediaService;
import com.bloducspauter.user.service.UploadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages = {"com.bloducspauter.ornament", "com.bloducspauter.bean"},
        scanBasePackageClasses= {UploadService.class, MediaService.class})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
@EnableRedisHttpSession
public class FeBlogOrnamentApp {
    public static void main(String[] args) {
        SpringApplication.run(FeBlogOrnamentApp.class);
    }

}
