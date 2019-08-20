package com.api.netty.demo.config.handler;

import com.api.netty.demo.config.router.HttpRouter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author 钱加祥
 * @date 2019/8/20
 * @update 更新content-length 10M
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    HttpRouter httpRouter;

    public ServerInitializer(HttpRouter httpRouter) {
        this.httpRouter = httpRouter;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        //HTTP 服务的解码器
        p.addLast(new HttpServerCodec());
        //HTTP 消息的合并处理
        p.addLast(new HttpObjectAggregator(10 * 1000 * 1024));
        //自己写的服务器逻辑处理
        p.addLast(new ServerHandler(httpRouter));
    }
}
