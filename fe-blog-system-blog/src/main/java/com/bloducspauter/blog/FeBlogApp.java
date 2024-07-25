package com.bloducspauter.blog;


import com.bloducspauter.category.mapper.FieldMapper;
import com.bloducspauter.ornament.mapper.MediaMapper;
import com.bloducspauter.category.service.FieldService;
import com.bloducspauter.ornament.service.MediaService;
import com.bloducspauter.user.mapper.UserMapper;
import com.bloducspauter.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.blog","com.bloducspauter.bean.config"}
        , scanBasePackageClasses = {FieldService.class, UserService.class, MediaService.class})
//启动服务注册发现功能
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
@MapperScan(basePackages = {"com.bloducspauter.blog.mapper", "com.bloducspauter.category.mapper"},
        basePackageClasses = {UserMapper.class, FieldMapper.class, MediaMapper.class})
public class FeBlogApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FeBlogApp.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);

    }
}
