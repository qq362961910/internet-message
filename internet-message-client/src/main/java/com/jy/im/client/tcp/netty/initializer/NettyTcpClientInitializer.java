package com.jy.im.client.tcp.netty.initializer;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyClientCommonMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.client.message.listener.CommonUserMessageListener;
import com.jy.im.client.message.listener.CommonLoginResponseMessageListener;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonMessageWriter;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonLoginMessageWriter;
import com.jy.im.client.tcp.netty.handler.NettyClientMessageHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyTcpClientInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解析输入数据
        NettyMessageAnalyserManager analyserManager = new NettyMessageAnalyserManager();
        analyserManager.addMessageAnalyser(new NettyClientCommonMessageAnalyser());
        //调用解析器
        pipeline.addLast(new NettyTcpDecoder(analyserManager));
        //消息字节输出
        pipeline.addLast(new NettyCommonLoginMessageWriter());
        pipeline.addLast(new NettyCommonMessageWriter());
        //输入消息处理器
        NettyClientMessageHandler clientMessageHandler = new NettyClientMessageHandler();
        //消息监听器
        clientMessageHandler.addMessageListener(new CommonUserMessageListener());
        clientMessageHandler.addMessageListener(new CommonLoginResponseMessageListener());
        pipeline.addLast(clientMessageHandler);
    }
}
