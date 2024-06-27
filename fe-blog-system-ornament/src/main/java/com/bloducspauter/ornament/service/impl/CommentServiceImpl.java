package com.bloducspauter.ornament.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.bloducspauter.bean.Comment;
import com.bloducspauter.ornament.mapper.CommentMapper;
import com.bloducspauter.ornament.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    /**
     * 增加对博客的评论
     */
    @Autowired
   private CommentMapper commentMapper;

    @Override
    public int add(Comment comment) {
        return commentMapper.insert(comment);
    }

    /**
     * 删除对博客的评论
     *
     */
    @Override
    public boolean deleted(int id) {
        int i = commentMapper.deleteById(id);
        return i != 0;
    }

    /**
     * 修改对博客的评论
     */
    @Override
    public Comment modify(Comment comment) {
        int i = commentMapper.updateById(comment);
        Comment result =commentMapper.selectById(comment.getId());
        if(i!=0){
            return result;
        }
        return null;
    }

    /**
     * 查询一条博客的所有评论
     *

     */
//    TODO
    @Override
    public List<Comment> selectAll(@Param("blogId") int blogId,@Param("page") int page,@Param("size") int size) {
        return commentMapper.selectAllComment(blogId,page,size);
    }

    /**
     * 查询当前用户下的评论总数
     */
    @Override
    public int selectCommentCount(@Param("blogId") int blogId) {
        QueryWrapper<Comment>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("blog_id",blogId);
        return commentMapper.selectCount(queryWrapper);
    }

    @Override
    public Comment selectCommentById(Integer cid) {
        return commentMapper.selectById(cid);
    }
}
