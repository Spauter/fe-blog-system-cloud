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

    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();


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
        log.info("有新的连接加入。。。{}", channel.id());
        CHANNELS.add(channel);
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
        CHANNEL_MAP.put(nettyJson.getLocation(), channel);
        sendToChannels(ctx, nettyJson);
    }


    /**
     * 处理未知消息
     */
    private void handleUnknownMessage(NettyJson nettyJson) {
        log.warn("未知类型消息: {}", nettyJson.getType());
    }

    /**
     * 发送消息到指定通道
     */
    private void sendToChannels(ChannelHandlerContext ctx, NettyJson nettyJson) {
        String json = JSONArray.toJSONString(nettyJson);
        try {
            String location = nettyJson.getLocation();
            List<NettyJson> nettyJsons = service.selectList(location);
            for (NettyJson n : nettyJsons) {
                Channel channel = CHANNEL_MAP.get(n.getLocation());
                channel.writeAndFlush(new TextWebSocketFrame(json));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送消息到指定通道失败,进行全局发送");
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
        service.delete(id);
    }
}
