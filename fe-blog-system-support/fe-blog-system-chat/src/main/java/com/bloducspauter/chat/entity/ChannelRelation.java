package com.bloducspauter.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Bloduc Spauter
 *
 */
@Document(collection = "channel_relation")
@Data
@AllArgsConstructor
public class ChannelRelation {
    @Id
    private String id;
    private String location;
}
