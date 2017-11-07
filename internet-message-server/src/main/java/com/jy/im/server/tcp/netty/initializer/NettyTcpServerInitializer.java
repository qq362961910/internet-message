package com.jy.im.server.tcp.netty.initializer;

import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.initializer.AbstractNettyTcpInitializer;
import com.jy.im.base.component.writer.tcp.netty.NettyMessageWriter;
import com.jy.im.server.helper.ApplicationContextHelper;
import com.jy.im.server.tcp.netty.handler.CommonLoginMessageHandler;
import com.jy.im.server.tcp.netty.handler.CommonUserMessageHandler;
import com.jy.im.server.tcp.netty.writer.CommonLoginResponseMessageWriter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Scope("prototype")
@Component
public class NettyTcpServerInitializer extends AbstractNettyTcpInitializer {

    @Autowired
    private ApplicationContextHelper applicationContextHelper;
    @Override
    public ByteToMessageDecoder getByteToMessageDecoder() {
        return applicationContextHelper.getBean(NettyTcpDecoder.class);
    }

    @Override
    public List<NettyMessageWriter> getNettyMessageWriters() {
        List<NettyMessageWriter> nettyMessageWriters = new ArrayList<>();
        nettyMessageWriters.add(applicationContextHelper.getBean(CommonLoginResponseMessageWriter.class));
        return nettyMessageWriters;
    }

    @Override
    public List<ChannelInboundHandlerAdapter> getMessageHandlerList() {
        List<ChannelInboundHandlerAdapter> channelInboundHandlerAdapters = new ArrayList<>();
        channelInboundHandlerAdapters.add(applicationContextHelper.getBean(CommonLoginMessageHandler.class));
        channelInboundHandlerAdapters.add(applicationContextHelper.getBean(CommonUserMessageHandler.class));
        return channelInboundHandlerAdapters;
    }
}

