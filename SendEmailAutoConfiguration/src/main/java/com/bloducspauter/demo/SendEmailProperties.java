package com.bloducspauter.demo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ConfigurationProperties("bs.send")
public class SendEmailProperties {
    private String host;
//    端口
    private String port;
//    是否发送邮件,默认发送
    private boolean openSendEmail=true;

}
