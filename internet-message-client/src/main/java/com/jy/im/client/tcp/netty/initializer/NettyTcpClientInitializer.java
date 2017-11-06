package com.jy.im.client.tcp.netty.initializer;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyClientMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.daemon.client.CommonMessageListener;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonMessageWriter;
import com.jy.im.base.component.writer.tcp.netty.NettyLoginMessageWriter;
import com.jy.im.client.tcp.netty.handler.ClientMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTcpClientInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        NettyMessageAnalyserManager analyserManager = new NettyMessageAnalyserManager();
        analyserManager.addMessageAnalyser(new NettyClientMessageAnalyser());
        pipeline.addLast(new NettyTcpDecoder(analyserManager));
        pipeline.addLast(new NettyLoginMessageWriter());
        pipeline.addLast(new NettyCommonMessageWriter());
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler();
        clientMessageHandler.addMessageListener(new CommonMessageListener());
        pipeline.addLast(clientMessageHandler);
    }
}
