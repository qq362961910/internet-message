package com.jy.im.client.tcp.netty.handler;

import com.jy.im.base.component.daemon.client.MessageListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NettyClientMessageHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(NettyClientMessageHandler.class);

    private List<MessageListener<?>> messageListenerList = new ArrayList<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        for (MessageListener messageListener : messageListenerList) {
            if (messageListener.apply(msg)) {
                messageListener.callback(msg);
                return;
            }
        }
        logger.warn("unhanded message: {}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client ---> active");
    }

    public void addMessageListener(MessageListener<?> messageListener) {
        if (messageListener != null) {
            messageListenerList.add(messageListener);
        }
    }
}
