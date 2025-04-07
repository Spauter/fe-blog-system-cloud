package com.bloducspauter.chat.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "chat")
@Data
public class AuditChatAPI {
    private String host = "https://lhwbshgl.market.alicloudapi.com";
    private String path = "/sensitive_words/filter";
    private String method = "POST";
    private String appcode = "你自己的AppCode";
}
