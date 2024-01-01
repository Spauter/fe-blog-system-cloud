package com.bloducspauter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bloducspauter.bean.Reply;
import com.bloducspauter.mapper.ReplyMapper;
import com.bloducspauter.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> selectAllResponseByCommentId(Integer commentId) {
        QueryWrapper<Reply>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",commentId);
        return replyMapper.selectList(queryWrapper);
    }
}
