package com.bloducspauter.service;

import com.bloducspauter.bean.Reply;
import java.util.List;

public interface ReplyService {
    List<Reply>selectAllResponseByCommentId(Integer commentId);
    int addReply(Reply reply);
}
