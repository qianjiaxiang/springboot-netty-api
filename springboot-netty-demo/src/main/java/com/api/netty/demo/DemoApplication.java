package com.api.netty.demo;

import com.api.netty.demo.config.handler.ServerInitializer;
import com.api.netty.demo.config.router.HttpRouter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author skyengine
 */
@SpringBootApplication
public class DemoApplication {
    public static final int PORT = 8080;
    public static void main(String[] args) throws InterruptedException {

        HttpRouter httpRouter = new HttpRouter();
        //添加Controller包，会自动识别包下面所有的类以及方法
        httpRouter.addRouter("com.api.netty.demo.controller");

        EventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap b = new ServerBootstrap();
        b.option(ChannelOption.SO_BACKLOG, 1024);
        b.group(group)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ServerInitializer(httpRouter));

        Channel ch = b.bind(PORT).sync().channel();
        ch.closeFuture().sync();

        SpringApplication.run(DemoApplication.class, args);
    }

}
