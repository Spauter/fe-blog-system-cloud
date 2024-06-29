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

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(FeChatApplication.class, args);
        Environment env = application.getEnvironment();
        String host = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        System.out.println("[----------------------------------------------------------]");
        System.out.println("聊天室启动成功！点击进入:\t http://" + host + ":" + port+"/test.html");
        System.out.println("[----------------------------------------------------------");

    }
    @Override
    public void run(String... args) throws Exception {
        new Thread(nettyWebSocketServer).start();
    }
}
