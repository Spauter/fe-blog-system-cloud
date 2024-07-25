package com.bloducspauter.ornament.service;


import com.bloducspauter.bean.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {


    /**
     * 增加对博客的评论
     *
     * @time    2021/8/9 14:42
     * @version 1.0
     *
     * @return true of false
     */
    int add(@Param("comment") Comment comment);

    /**
     * 删除对博客的评论
     *
     * @time    2021/8/9 14:42
     * @version 1.0
     *
     * @return true of false
     */
    boolean deleted(@Param("id")int id);

    /**
     * 修改对博客的评论
     *
     * @time    2021/8/9 14:42
     * @version 1.0
     *
     * @return true of false
     */
    Comment modify(@Param("comment") Comment comment);
    /**
     * 查询一条博客的所有评论
     * @time    2021/8/9 15:35
     * @version 1.0
     *
     * @return
     */
    List<Comment> selectAll(@Param("blogId") int blogId,@Param("page") int page,@Param("size") int size);

    /**
     * 查询当前用户下的评论总数
     * @param
     * @return
     */

    int selectCommentCount(@Param("blogId") int blogId);


    Comment selectCommentById(Integer cid);

}
