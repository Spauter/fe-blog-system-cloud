package com.bloducspauter.chat.service.impl;

import com.bloducspauter.chat.entity.NettyJson;
import com.bloducspauter.chat.service.NettyJsonService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author Bloduc Spauter
 *
 */
@Service
@Slf4j
public class NettyJsonServiceImpl implements NettyJsonService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(NettyJson nettyJson) {
        try {
            mongoTemplate.save(nettyJson);
            log.info("save nettyJson success");
        } catch (Exception e) {
            log.error("save nettyJson failed because {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            mongoTemplate.remove(id);
            log.info("delete nettyJson success");
        }catch (Exception e) {
            log.error("delete nettyJson failed because {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<NettyJson> selectList(String location) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("location").is(location));
            log.info("select  nettyJsons success");
            return mongoTemplate.find(query, NettyJson.class);
        } catch (Exception e) {
            log.error("select nettyJsons failed because {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public NettyJson selectOne(String id) {
        try {
            Query query=new Query();
            query.addCriteria(Criteria.where("id").is(id));
            log.info("select a nettyJson success");
            return mongoTemplate.findOne(query, NettyJson.class);
        }catch (Exception e) {
            log.error("select a nettyJson failed because {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
