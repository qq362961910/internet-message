package com.jy.im.client.tcp.initializer;

import com.jy.im.client.tcp.handler.CommonMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTcpClientInitializer extends ChannelInitializer<NioSocketChannel>{

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new CommonMessageHandler());
    }
}
