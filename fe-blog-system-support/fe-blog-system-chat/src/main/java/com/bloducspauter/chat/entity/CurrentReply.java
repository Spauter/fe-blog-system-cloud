package com.bloducspauter.chat.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 用户回复
 * @author Bloduc Spauter
 *
 */
@Document("reply")
@Data
public class CurrentReply {
    @Id
    private Integer rid;
    private String account;
    private LocalDateTime createTime;
    private String content;
}
