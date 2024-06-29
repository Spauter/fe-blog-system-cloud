package com.bloducspauter.chat.netty;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "netty")
@Data
public class Netty {
    /**
     * netty监听的端口
     */
    private int port;
    /**
     * websocket访问路径
     */
    private String path;
}