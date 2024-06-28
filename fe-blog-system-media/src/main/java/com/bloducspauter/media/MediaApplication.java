package com.bloducspauter.media;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Media启动类
 * @author Bloduc Spauter
 *
 */
@SpringBootApplication(scanBasePackages = {"com.bloducspauter.media","com.bloducspauter.bean"})
public class MediaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MediaApplication.class, args);
    }
}
