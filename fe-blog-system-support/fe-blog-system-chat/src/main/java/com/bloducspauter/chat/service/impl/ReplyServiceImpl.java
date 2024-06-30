package com.bloducspauter.chat.service.impl;

import com.bloducspauter.chat.entity.CurrentReply;
import com.bloducspauter.chat.service.ReplyService;
import jakarta.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Bloduc Spauter
 */
@Service
public class ReplyServiceImpl implements ReplyService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(CurrentReply currentReply) {
        mongoTemplate.save(currentReply);
    }

    @Override
    public List<CurrentReply> getReply(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.find(query, CurrentReply.class);
    }

    @Override
    public List<CurrentReply> getReplyByPage(String id, int pageNo, int pageSize) {
       Query query = new Query();
       Criteria criteria = Criteria.where("id").is(id);
       query.addCriteria(criteria);
       query.skip(pageNo - 1);
       query.limit(pageSize);
       return mongoTemplate.find(query, CurrentReply.class);
    }
}
