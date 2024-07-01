package com.bloducspauter.chat;



import com.bloducspauter.chat.netty.NettyWebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(scanBasePackages = {"com.bloducspauter.chat","com.bloducspauter.bean"})
@EnableDiscoveryClient
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
