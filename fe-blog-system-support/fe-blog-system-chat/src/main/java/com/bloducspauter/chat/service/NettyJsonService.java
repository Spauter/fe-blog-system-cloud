package com.bloducspauter.chat.service;

import com.bloducspauter.chat.entity.NettyJson;
import java.util.List;
/**
 * @author Bloduc Spauter
 *
 */
public interface NettyJsonService {
    /**
     *  保存聊天记录
     * @param nettyJson
     */
    void save(NettyJson nettyJson);

    /**
     * 删除聊天记录
     * @param id
     */
    void delete(String id);

    /**
     * 查询相关记录
     * @param location
     */
    List<NettyJson> selectList(String location);

    NettyJson selectOne(String id);

    void update(NettyJson nettyJson);
}
