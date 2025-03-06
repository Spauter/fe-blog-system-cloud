package com.bloducspauter.user;

import com.bloducspauter.media.config.MinioConfig;
import com.bloducspauter.media.mapper.MediaFilesMapper;
import com.bloducspauter.media.service.MediaService;
import com.bloducspauter.user.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages =
        {"com.bloducspauter.user", "com.bloducspauter.bean"},scanBasePackageClasses = {MediaService.class, MinioConfig.class})
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
@MapperScan(basePackageClasses = {MediaFilesMapper.class, UserMapper.class})
public class FeBlogUserApp{
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FeBlogUserApp.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }
}
