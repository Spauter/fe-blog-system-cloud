package com.bloducspauter.chat.service;

import com.bloducspauter.chat.entity.CurrentReply;
import java.util.List;
/**
 *
 * Reply 服务层
 * @author Bloduc Spauter
 *
 */
public interface ReplyService {
    /**
     * 保存回复
     */
    int save(CurrentReply currentReply);

    /**
     * 根据博客的评论ID查询所有评论
     * @param id
     * @return
     */
    List<CurrentReply>getReply(String id);

    /**
     *  根据博客的评论ID分页查询部分评论
     * @param id 评论Id
     * @param pageNo 页码
     * @param pageSize 每页显示的数量
     */
    List<CurrentReply>getReplyByPage(String id,int pageNo,int pageSize);

}
