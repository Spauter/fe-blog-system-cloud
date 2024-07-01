package com.bloducspauter.chat;

import com.bloducspauter.chat.netty.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.bloducspauter.chat","com.bloducspauter.bean"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.bloducspauter.api"})
public class FeChatApplication implements CommandLineRunner {
    @Autowired
    private NettyWebSocketServer nettyWebSocketServer;

    public static void main(String[] args) {
        SpringApplication.run(FeChatApplication.class, args);

    }
    @Override
    public void run(String... args) throws Exception {
        new Thread(nettyWebSocketServer).start();
    }
}
