package com.bloducspauter.chat.service;

import com.bloducspauter.chat.entity.CurrentComment;

import java.util.List;

/**
 * 评论服务层
 *
 * @author Bloduc Spauter
 */
public interface CommentService {
    /**
     * 保存评论
     *
     * @param comment 评论
     */
    void save(CurrentComment comment);

    /**
     * 根基Blog的id获取评论
     * @param blogId 博客的id
     */
    List<CurrentComment> getCurrentComment(String blogId);

    /**
     * 分页查询博客下的评论
     * @param blogId 博客Id
     * @param pageNo 当前页
     * @param pageSize 每一页的数量
     */
    List<CurrentComment>getCurrentCommentByPage(String blogId,int pageNo,int pageSize);
}
