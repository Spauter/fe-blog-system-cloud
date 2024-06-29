package com.bloducspauter.chat.netty;

import com.bloducspauter.chat.handler.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 通道初始化对象
 * @author 流星
 */
@Component
public class WebSocketChannelConfig extends ChannelInitializer<Channel> {

    @Autowired
    private Netty netty;
    @Autowired
    private WebSocketHandler webSocketHandler;

//    @Resource
//    private BinaryWebSocketFrameHandler binaryWebSocketFrameHandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        // 对http协议的支持.
        pipeline.addLast(new HttpServerCodec());
        // 对大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // HttpObjectAggregator将多个信息转化成单一的request或者response对象
        pipeline.addLast(new HttpObjectAggregator(8000));
        // 将http协议升级为ws协议. 对websocket的支持
        pipeline.addLast(new WebSocketServerProtocolHandler(netty.getPath()));
        // 自定义处理handler
        pipeline.addLast(webSocketHandler);
    }
}
