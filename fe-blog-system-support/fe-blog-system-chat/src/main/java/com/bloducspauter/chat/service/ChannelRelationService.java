package com.bloducspauter.chat.service;

import com.bloducspauter.chat.entity.ChannelRelation;


import java.util.*;

/**
 * @author Bloduc Spauter
 */
public interface ChannelRelationService {


    void delete(String id, String location);

    ChannelRelation selectOne(String id);

    List<ChannelRelation> selectList(String location);

    void save(String id, String location);
}
