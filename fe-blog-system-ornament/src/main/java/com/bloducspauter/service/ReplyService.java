package com.bloducspauter.service;

import com.bloducspauter.bean.Reply;
import com.bloducspauter.bean.User;

import java.util.List;

public interface ReplyService {
    List<Reply>selectAllResponseByCommentId(Integer commentId);

}
