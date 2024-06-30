package com.bloducspauter.chat.service.impl;

import com.bloducspauter.chat.entity.CurrentComment;
import com.bloducspauter.chat.service.CommentService;
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
public class CommentServiceImpl implements CommentService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void save(CurrentComment comment) {
        mongoTemplate.save(comment);
    }

    @Override
    public List<CurrentComment> getCurrentComment(String blogId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("blog_id").is(blogId);
        query.addCriteria(criteria);
        return mongoTemplate.find(query, CurrentComment.class);
    }

    @Override
    public List<CurrentComment> getCurrentCommentByPage(String blogId, int pageNo, int pageSize) {
        Query query = new Query();
        Criteria criteria = Criteria.where("blog_id").is(blogId);
        query.addCriteria(criteria);
        query.limit(pageSize);
        query.skip(pageNo-1);
        return mongoTemplate.find(query, CurrentComment.class);
    }
}
