package com.bloducspauter.category.service;

import com.bloducspauter.bean.Reply;
import java.util.List;

public interface ReplyService {
    List<Reply>selectAllResponseByCommentId(Integer commentId,Integer page,Integer size);
    int addReply(Reply reply);
}
