package com.jy.im.base.component.initializer;

import com.jy.im.base.component.writer.tcp.netty.NettyMessageWriter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public abstract class AbstractNettyTcpInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //decoder
        pipeline.addLast(getByteToMessageDecoder());
        //encoder
        List<NettyMessageWriter> messageToByteEncoders = getNettyMessageWriters();
        if(messageToByteEncoders != null && !messageToByteEncoders.isEmpty()) {
            for(MessageToByteEncoder encoder: messageToByteEncoders) {
                pipeline.addLast(encoder);
            }
        }
        //handler
        List<ChannelInboundHandlerAdapter> messageHandlers = getMessageHandlerList();
        if(messageHandlers!= null && !messageHandlers.isEmpty()) {
            for(ChannelInboundHandlerAdapter handlerAdapter: messageHandlers) {
                pipeline.addLast(handlerAdapter);
            }
        }
    }

    public abstract ByteToMessageDecoder getByteToMessageDecoder();

    public abstract List<NettyMessageWriter> getNettyMessageWriters();

    public abstract List<ChannelInboundHandlerAdapter> getMessageHandlerList();

}
