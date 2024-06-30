package com.bloducspauter.chat.handler;


import com.alibaba.fastjson.JSONArray;
import com.bloducspauter.chat.entity.ChannelRelation;
import com.bloducspauter.chat.entity.CurrentComment;
import com.bloducspauter.chat.entity.CurrentReply;
import com.bloducspauter.chat.entity.NettyJson;
import com.bloducspauter.chat.enums.ReceptionType;
import com.bloducspauter.chat.service.ChannelRelationService;
import com.bloducspauter.chat.service.CommentService;
import com.bloducspauter.chat.service.ReplyService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义处理类
 * TextWebSocketFrame: websocket数据是帧的形式处理
 *
 * @author 流星
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Map<ChannelRelation, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();

    @Resource
    private ChannelRelationService channelRelationService;

    @Resource
    private CommentService commentService;

    @Resource
    ReplyService replyService;


    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("有新的连接加入。。。{}", channel.id());
    }

    /**
     * 通道未就绪事件
     *
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //当有客户端断开连接的时候,就移除对应的通道
        String id = channel.id().asLongText();
        channelRelationService.save(id, null);
        log.info("有连接已经断开。。。");
    }

    /**
     * 通道读取事件
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        NettyJson nettyJson = JSONArray.parseObject(msg, NettyJson.class);
        switch (ReceptionType.getReceptionType(nettyJson.getType())) {
            case INIT:
                handleInitMessage(ctx, nettyJson);
                break;
            case COMMENT:
                handleCommentMessage(ctx,nettyJson);
                break;
            case REPLY:
                handleReplyMessage(ctx,nettyJson);
                break;
            case Unknown:
                handleUnknownMessage(nettyJson);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化通道
     * @param nettyJson 初始化消息
     */
    private void handleInitMessage(ChannelHandlerContext ctx, NettyJson nettyJson) {
        String id = ctx.channel().id().asLongText();
        String location = nettyJson.getLocation();
        channelRelationService.save(id, location);
        log.info("初始化通道 id: {}", id);
    }

    /**
     *  处理评论通道
     */
    private void handleCommentMessage(ChannelHandlerContext ctx,NettyJson nettyJson) {
        CurrentComment c = new CurrentComment();
        c.setId(String.valueOf(nettyJson.getId()));
        c.setAccount(nettyJson.getAccount());
        c.setContent(nettyJson.getContent());
        c.setCreateTime(LocalDateTime.now());
        commentService.save(c);
        log.info("评论成功");
        sendToChannels(ctx,nettyJson.getLocation(), nettyJson.getContent(),c.getId());
    }

    /**
     * 处理回复频道
     */
    private void handleReplyMessage(ChannelHandlerContext ctx,NettyJson nettyJson) {
        CurrentReply r = new CurrentReply();
        r.setRid(String.valueOf(nettyJson.getId()));
        r.setAccount(nettyJson.getAccount());
        r.setContent(nettyJson.getContent());
        r.setCreateTime(LocalDateTime.now());
        replyService.save(r);
        log.info("回复成功");
        sendToChannels(ctx,nettyJson.getLocation(), nettyJson.getContent(),r.getRid());
    }

    /**
     * 处理未知消息
     */
    private void handleUnknownMessage(NettyJson nettyJson) {
        log.warn("未知类型消息: {}", nettyJson.getType());
    }

    /**
     * 发送消息到指定通道
     *
     */
    private void sendToChannels(ChannelHandlerContext ctx,String location, String content,String id) {
        List<ChannelRelation> channelRelations = channelRelationService.selectList(location);
        Channel thisChannel = ctx.channel();
        SendContentToChannels sendContentToChannels = new SendContentToChannels();
        sendContentToChannels.setCid(id);
        sendContentToChannels.setContent(content);
        String sendContent = JSONArray.toJSONString(sendContentToChannels);
        for (ChannelRelation channelRelation : channelRelations) {
            Channel channel = CHANNEL_MAP.get(channelRelation);
            if (channel != null && channel != thisChannel) {
                channel.writeAndFlush(new TextWebSocketFrame(sendContent));
            }
        }
    }

    /**
     * 异常处理事件
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Channel channel = ctx.channel();
        //移除集合
        String id = channel.id().asLongText();
        log.info("异常断开连接。。。{}", id);
        channelRelationService.delete(id, null);
    }
}

@Setter
class SendContentToChannels {
    private String cid;
    private String content;
}