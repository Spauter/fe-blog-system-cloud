package com.bloducspauter.chat.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;


/**
 * 用户评论
 *
 * @author Bloduc Spauter
 */
@Document("comment")
@Data
public class CurrentComment {
    @Id
    private String id;
    private Integer blogId;
    private String content;
    private LocalDateTime createTime;
    private String account;
}
