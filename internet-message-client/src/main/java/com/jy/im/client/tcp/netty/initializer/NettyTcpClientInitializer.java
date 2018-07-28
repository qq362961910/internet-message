package com.jy.im.client.tcp.netty.initializer;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyClientCommonMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.initializer.AbstractNettyTcpInitializer;
import com.jy.im.base.component.translator.tcp.netty.common.impl.CommonLoginResponseMessageTranslator;
import com.jy.im.base.component.translator.tcp.netty.common.impl.CommonTicketInvalidServerNotificationMessageTranslator;
import com.jy.im.base.component.translator.tcp.netty.common.impl.CommonUserStringMessageTranslator;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonLoginRequestMessageWriter;
import com.jy.im.base.component.writer.tcp.netty.NettyCommonUserStringMessageWriter;
import com.jy.im.base.component.writer.tcp.netty.NettyMessageWriter;
import com.jy.im.client.message.listener.CommonLoginFailResponseMessageListener;
import com.jy.im.client.message.listener.CommonLoginSuccessResponseMessageListener;
import com.jy.im.client.message.listener.CommonTicketInvalidServerNotificationMessageListener;
import com.jy.im.client.message.listener.CommonUserStringMessageListener;
import com.jy.im.client.tcp.netty.handler.NettyClientBaseMessageHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public class NettyTcpClientInitializer extends AbstractNettyTcpInitializer {


    @Override
    public ByteToMessageDecoder getByteToMessageDecoder() {
        NettyMessageAnalyserManager analyserManager = new NettyMessageAnalyserManager();
        NettyClientCommonMessageAnalyser nettyClientCommonMessageAnalyser = new NettyClientCommonMessageAnalyser();
        //添加消息翻译器
        nettyClientCommonMessageAnalyser.addMessageTranslator(new CommonLoginResponseMessageTranslator());
        nettyClientCommonMessageAnalyser.addMessageTranslator(new CommonUserStringMessageTranslator());
        nettyClientCommonMessageAnalyser.addMessageTranslator(new CommonTicketInvalidServerNotificationMessageTranslator());
        analyserManager.addMessageAnalyser(nettyClientCommonMessageAnalyser);
        return new NettyTcpDecoder(analyserManager);
    }

    @Override
    public List<NettyMessageWriter> getNettyMessageWriters() {
        List<NettyMessageWriter> nettyMessageWriters = new ArrayList<>();
        nettyMessageWriters.add(new NettyCommonLoginRequestMessageWriter());
        nettyMessageWriters.add(new NettyCommonUserStringMessageWriter());
        return nettyMessageWriters;
    }

    @Override
    public List<ChannelInboundHandlerAdapter> getMessageHandlerList() {
        NettyClientBaseMessageHandler clientMessageHandler = new NettyClientBaseMessageHandler();
        clientMessageHandler.addMessageListener(new CommonUserStringMessageListener());
        clientMessageHandler.addMessageListener(new CommonLoginSuccessResponseMessageListener());
        clientMessageHandler.addMessageListener(new CommonLoginFailResponseMessageListener());
        clientMessageHandler.addMessageListener(new CommonTicketInvalidServerNotificationMessageListener());
        List<ChannelInboundHandlerAdapter> channelInboundHandlerAdapters = new ArrayList<>();
        channelInboundHandlerAdapters.add(clientMessageHandler);
        return channelInboundHandlerAdapters;
    }
}
