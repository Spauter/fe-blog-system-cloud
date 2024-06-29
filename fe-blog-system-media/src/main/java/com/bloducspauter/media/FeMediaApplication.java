package com.bloducspauter.media;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Media启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.media","com.bloducspauter.bean"})
@EnableRedisHttpSession
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
@MapperScan("com.bloducspauter.media.mapper")
public class FeMediaApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FeMediaApplication.class);
        springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
    }
}
