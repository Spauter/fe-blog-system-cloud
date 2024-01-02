package com.bloducspauter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bloducspauter.bean.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
    List<Reply> selectReplyByPage(@Param("id") int id, @Param("page") int page,@Param("size") int size);
}
