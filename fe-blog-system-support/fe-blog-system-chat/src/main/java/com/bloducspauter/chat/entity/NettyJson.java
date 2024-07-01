package com.bloducspauter.chat.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 对应:<p>
 * const NETTY_JSON={
 * blogId: 0,
 * id: 0,
 * contentId: 0,
 * account: '',
 * type: '',
 * location: 'index.html',
 * content : '',
 * }
 *
 * @author Bloduc Spauter
 */
@Data
@Document("netty_json")
public class NettyJson {

    private String id;
    private String blogId;
    private String contentId;
    private String account;
    private String type;
    private String location;
    private String content;
}

