package com.bloducspauter.ornament;

import com.bloducspauter.ornament.mapper.MediaMapper;
import com.bloducspauter.ornament.service.MediaService;
import com.bloducspauter.user.mapper.UserMapper;
import com.bloducspauter.user.service.UploadService;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
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

@MapperScan(basePackages = {"com.bloducspauter.ornament.mapper"}
        ,basePackageClasses = {FastFileStorageClient.class, MediaMapper.class, UserMapper.class})
public class FeBlogOrnamentApp {
    public static void main(String[] args) {
        SpringApplication.run(FeBlogOrnamentApp.class);
    }

}
