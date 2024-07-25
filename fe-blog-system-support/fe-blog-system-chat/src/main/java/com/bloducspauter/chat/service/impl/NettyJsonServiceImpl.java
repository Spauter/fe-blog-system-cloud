package com.bloducspauter.chat.service.impl;

import com.bloducspauter.chat.entity.NettyJson;
import com.bloducspauter.chat.service.NettyJsonService;
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

    @Override
    public void update(NettyJson nettyJson) {
       Update u = new Update();
        u.set("location", nettyJson.getLocation());
        try {
            mongoTemplate.updateFirst(new Query(Criteria.where("id").is(nettyJson.getId())), u, NettyJson.class);
            log.info("update nettyJson success");
        } catch (Exception e) {
            log.error("update nettyJson failed because {}", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<NettyJson>selectListByPage(String location,int pageNo,int pageSize){
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("location").is(location));
            query.skip(pageNo-1);
            query.limit(pageSize);
            log.info("select  nettyJsons by page success");
            return mongoTemplate.find(query, NettyJson.class);
        } catch (Exception e) {
            log.error("select nettyJsons by page was failed because {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<NettyJson> findAll() {
        return mongoTemplate.findAll(NettyJson.class);
    }
}
