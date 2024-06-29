package com.bloducspauter.chat.handler;

import com.bloducspauter.bean.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
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

    public static List<Channel> channelList = new CopyOnWriteArrayList<>();

    @Resource
    private RedisTemplate<String,Object>redisTemplate;
    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        User user= (User) redisTemplate.opsForValue().get("user");
        if (user == null) {
            log.info("用户未登录");
        } else {
            log.info("用户已登录:{}",user.getAccount());
        }
        //当有新的客户端连接的时候, 将通道放入集合
        channelList.add(channel);
        log.info("有新的连接加入。。。");
    }

    /**
     * 通道未就绪事件
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //当有客户端断开连接的时候,就移除对应的通道
        channelList.remove(channel);
        log.info("有连接已经断开。。。");
    }

    /**
     * 读就绪事件
     *
     * @param ctx
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String msg = textWebSocketFrame.text();
        log.info("msg:{}", msg);
        //当前发送消息的通道, 当前发送的客户端连接
        Channel channel = ctx.channel();
        for (Channel channel1 : channelList) {
            //排除自身通道
            if (channel != channel1) {
                //writeAndFlush 是 Channel 接口的方法，用于向通道写入消息并立即将其发送给对端。
                channel1.writeAndFlush(new TextWebSocketFrame(msg));
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
        channelList.remove(channel);
    }
}
