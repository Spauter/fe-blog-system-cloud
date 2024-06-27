package com.bloducspauter.ornament.service.impl;

import com.bloducspauter.bean.Reply;
import com.bloducspauter.ornament.mapper.ReplyMapper;
import com.bloducspauter.ornament.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Reply> selectAllResponseByCommentId(Integer commentId, Integer page, Integer size) {
        return replyMapper.selectReplyByPage(commentId, page, size);
    }

    @Override
    public int addReply(Reply reply) {
        return replyMapper.insert(reply);
    }
}
