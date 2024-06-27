package com.bloducspauter.ornament.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bloducspauter.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询一条博客下的所有评论
     * @param blogId 博客id, page当前页数, size每页显示的记录数
     * @return
     */
    List<Comment> selectAllComment(@Param("blogId") int blogId,@Param("page") int page,@Param("size") int size);

}
