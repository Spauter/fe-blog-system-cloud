package com.bloducspauter.blog.config;

import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfiguration {
    @Bean
    public feign.Logger logger() {
        return new Slf4jLogger();
    }
}
