package com.jy.im.client.tcp.initializer;

import com.jy.im.base.component.daemon.listener.CommonMessageListener;
import com.jy.im.base.component.writer.NettyCommonMessageWriter;
import com.jy.im.base.component.writer.NettyLoginMessageWriter;
import com.jy.im.client.tcp.handler.ClientMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTcpClientInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new NettyLoginMessageWriter());
        pipeline.addLast(new NettyCommonMessageWriter());
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler();
        clientMessageHandler.addMessageListener(new CommonMessageListener());
        pipeline.addLast(clientMessageHandler);
    }
}
