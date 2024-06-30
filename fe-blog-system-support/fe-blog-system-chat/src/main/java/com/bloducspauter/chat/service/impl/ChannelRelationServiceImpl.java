package com.bloducspauter.chat.service.impl;

import com.bloducspauter.chat.entity.ChannelRelation;
import com.bloducspauter.chat.service.ChannelRelationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Bloduc Spauter
 */
@Slf4j
@Service
public class ChannelRelationServiceImpl implements ChannelRelationService {

    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public void delete(String id, String location) {
        if (id == null && location == null) {
            log.error("id and location cannot be null at the same time when deleting");
            return;
        }
        Query query = new Query();
        if (id != null) {
            query.addCriteria(Criteria.where("id").is(id));
        }
        if (location != null) {
            query.addCriteria(Criteria.where("location").is(location));
        }
        mongoTemplate.remove(query, ChannelRelation.class);
    }

    @Override
    public ChannelRelation selectOne(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, ChannelRelation.class);
    }

    @Override
    public List<ChannelRelation> selectList(String location) {
        if (location == null) {
            log.error("location cannot be null");
            return null;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("location").is(location));
        return mongoTemplate.find(query, ChannelRelation.class);
    }

    @Override
    public void save(String id, String location) {
        if (id == null || location == null) {
            log.error("id and location cannot be null at the same time when saving or updating");
            return;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        ChannelRelation channelRelation = mongoTemplate.findOne(query, ChannelRelation.class);
        if (channelRelation == null) {
            log.info("no channelRelation found with id:{} , create a new one", id);
            mongoTemplate.insert(new ChannelRelation(id, location));
            return;
        }
        Update update = new Update();
        update.set("location", location);
        mongoTemplate.updateFirst(query, update, ChannelRelation.class);
        log.info("channelRelation with id:{} updated successfully which location is:{}", id, location);
    }
}