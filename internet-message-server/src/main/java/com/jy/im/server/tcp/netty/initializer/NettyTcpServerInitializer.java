package com.jy.im.server.tcp.netty.initializer;

import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.initializer.AbstractNettyTcpInitializer;
import com.jy.im.base.component.writer.tcp.netty.NettyMessageWriter;
import com.jy.im.server.helper.ApplicationContextHelper;
import com.jy.im.server.tcp.netty.handler.CommonLoginMessageHandler;
import com.jy.im.server.tcp.netty.handler.CommonUserStringMessageHandler;
import com.jy.im.server.tcp.netty.writer.CommonLoginFailResponseMessageWriter;
import com.jy.im.server.tcp.netty.writer.CommonLoginSuccessResponseMessageWriter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.ArrayList;
import java.util.List;

public class NettyTcpServerInitializer extends AbstractNettyTcpInitializer {

    private ApplicationContextHelper applicationContextHelper;

    @Override
    public ByteToMessageDecoder getByteToMessageDecoder() {
        return applicationContextHelper.getBean(NettyTcpDecoder.class);
    }

    @Override
    public List<NettyMessageWriter> getNettyMessageWriters() {
        List<NettyMessageWriter> nettyMessageWriters = new ArrayList<>();
        nettyMessageWriters.add(applicationContextHelper.getBean(CommonLoginSuccessResponseMessageWriter.class));
        nettyMessageWriters.add(applicationContextHelper.getBean(CommonLoginFailResponseMessageWriter.class));
        return nettyMessageWriters;
    }

    @Override
    public List<ChannelInboundHandlerAdapter> getMessageHandlerList() {
        List<ChannelInboundHandlerAdapter> channelInboundHandlerAdapters = new ArrayList<>();
        channelInboundHandlerAdapters.add(applicationContextHelper.getBean(CommonLoginMessageHandler.class));
        channelInboundHandlerAdapters.add(applicationContextHelper.getBean(CommonUserStringMessageHandler.class));
        return channelInboundHandlerAdapters;
    }

    public NettyTcpServerInitializer(ApplicationContextHelper applicationContextHelper) {
        this.applicationContextHelper = applicationContextHelper;
    }
}

