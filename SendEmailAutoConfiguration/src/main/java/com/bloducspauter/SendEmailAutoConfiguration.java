package com.bloducspauter;

import com.bloducspauter.demo.BsSendEmailFunction;
import com.bloducspauter.demo.SendEmailProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(BsSendEmailFunction.class)
@EnableConfigurationProperties({SendEmailProperties.class})
public class SendEmailAutoConfiguration {
    @Bean
    public BsSendEmailFunction bsSendEmailFunction(){
        return new BsSendEmailFunction();
        
    }
}
