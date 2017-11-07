package com.jy.im.server.tcp.netty.initializer;

import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.server.helper.ApplicationContextHelper;
import com.jy.im.server.tcp.netty.handler.CommonUserMessageHandler;
import com.jy.im.server.tcp.netty.handler.CommonLoginMessageHandler;
import com.jy.im.server.tcp.netty.writer.CommonLoginResponseMessageWriter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class NettyTcpServerInitializer extends ChannelInitializer {

    @Autowired
    private ApplicationContextHelper applicationContextHelper;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(applicationContextHelper.getBean(NettyTcpDecoder.class));
        channelPipeline.addLast(applicationContextHelper.getBean(CommonLoginResponseMessageWriter.class));
        channelPipeline.addLast(applicationContextHelper.getBean(CommonLoginMessageHandler.class));
        channelPipeline.addLast(applicationContextHelper.getBean(CommonUserMessageHandler.class));

    }
}

