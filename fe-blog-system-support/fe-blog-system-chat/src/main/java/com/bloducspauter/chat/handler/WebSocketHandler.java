package com.bloducspauter.chat.handler;


import com.alibaba.fastjson.JSONArray;
import com.bloducspauter.chat.entity.NettyJson;
import com.bloducspauter.chat.service.NettyJsonService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private String nettyLocation;

    private static final Map<Channel, String> CHANNEL_MAP = new ConcurrentHashMap<>();


    @Resource
    NettyJsonService service;

    private static final List<Channel> CHANNELS = new CopyOnWriteArrayList<>();

    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        CHANNELS.add(channel);
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
        CHANNELS.remove(channel);
        CHANNEL_MAP.remove(channel);
        ctx.close();
        //当有客户端断开连接的时候,就移除对应的通道
        log.info("有连接已经断开。。。");
    }

    /**
     * 通道读取事件
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        NettyJson nettyJson = JSONArray.parseObject(msg, NettyJson.class);
        log.info("收到消息:{}", nettyJson);
        // 处理消息
        service.save(nettyJson);
        //发送消息
        Channel channel = ctx.channel();
        //存储相关信息
        nettyLocation = nettyJson.getLocation();
        CHANNEL_MAP.put(channel, nettyLocation);
        sendToChannels(ctx, nettyJson);
    }


    /**
     * 发送消息到指定通道
     */
    private void sendToChannels(ChannelHandlerContext ctx, NettyJson nettyJson) {

        try {
            String location = nettyJson.getLocation();
            CHANNELS.forEach(l -> {
                if (CHANNEL_MAP.get(l).equals(location)) {
                    String json = JSONArray.toJSONString(nettyJson);
                    l.writeAndFlush(new TextWebSocketFrame(json));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送消息到指定通道失败,进行全局发送");
            String json = JSONArray.toJSONString(nettyJson);
            for (Channel channel : CHANNELS) {
                channel.writeAndFlush(new TextWebSocketFrame(json));
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
        String id = channel.id().asShortText();
        log.info("异常断开连接。。。{}", id);
        CHANNELS.remove(channel);
        CHANNEL_MAP.remove(channel);
        ctx.close();
    }
}
