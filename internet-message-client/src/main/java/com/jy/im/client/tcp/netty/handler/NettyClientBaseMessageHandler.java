package com.jy.im.client.tcp.netty.handler;

import com.jy.im.base.component.daemon.client.AbstractMessageListener;
import com.jy.im.common.entity.BaseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class NettyClientBaseMessageHandler extends SimpleChannelInboundHandler<BaseMessage> {

    private static final Logger logger = LoggerFactory.getLogger(NettyClientBaseMessageHandler.class);

    private List<AbstractMessageListener> messageListenerList = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessage msg) {
        for (AbstractMessageListener messageListener : messageListenerList) {
            if (messageListener.apply(msg)) {
                messageListener.callback(msg);
                return;
            }
        }
        logger.warn("unhanded message: {}", msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("client ---> active");
    }

    public void addMessageListener(AbstractMessageListener messageListener) {
        if (messageListener != null) {
            messageListenerList.add(messageListener);
        }
    }
}
