package com.jy.im.client.tcp.netty.initializer;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyClientCommonMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.initializer.AbstractNettyTcpInitializer;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonLoginMessageWriter;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonUserMessageWriter;
import com.jy.im.base.component.writer.tcp.netty.NettyMessageWriter;
import com.jy.im.client.message.listener.CommonLoginResponseMessageListener;
import com.jy.im.client.message.listener.CommonUserMessageListener;
import com.jy.im.client.tcp.netty.handler.NettyClientMessageHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public class NettyTcpClientInitializer extends AbstractNettyTcpInitializer {

    @Override
    public ByteToMessageDecoder getByteToMessageDecoder() {
        NettyMessageAnalyserManager analyserManager = new NettyMessageAnalyserManager();
        analyserManager.addMessageAnalyser(new NettyClientCommonMessageAnalyser());
        return new NettyTcpDecoder(analyserManager);
    }

    @Override
    public List<NettyMessageWriter> getNettyMessageWriters() {
        List<NettyMessageWriter> nettyMessageWriters = new ArrayList<>();
        nettyMessageWriters.add(new NettyCommonLoginMessageWriter());
        nettyMessageWriters.add(new NettyCommonUserMessageWriter());
        return nettyMessageWriters;
    }

    @Override
    public List<ChannelInboundHandlerAdapter> getMessageHandlerList() {
        NettyClientMessageHandler clientMessageHandler = new NettyClientMessageHandler();
        clientMessageHandler.addMessageListener(new CommonUserMessageListener());
        clientMessageHandler.addMessageListener(new CommonLoginResponseMessageListener());
        List<ChannelInboundHandlerAdapter> channelInboundHandlerAdapters = new ArrayList<>();
        channelInboundHandlerAdapters.add(clientMessageHandler);
        return channelInboundHandlerAdapters;
    }
}
