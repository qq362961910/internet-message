package com.jy.im.base.component.daemon.client;

import com.jy.im.base.component.daemon.listener.MessageListener;
import com.jy.im.common.constants.CommonMessageType;
import com.jy.im.common.entity.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CommonMessageHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(CommonMessageHandler.class);

    private List<MessageListener<?>> messageListenerList = new ArrayList<>();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        for (MessageListener messageListener: messageListenerList) {
            if(messageListener.apply(msg)) {
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
    public CommonMessage buildStringCommonMessage(String content) {
        CommonMessage message = new CommonMessage();
        message.setMessageType(CommonMessageType.STRING.value);
        message.setLength((short)content.getBytes().length);
        message.setContent(content.getBytes());
        return message;
    }
    public void addMessageListener(MessageListener<?> messageListener) {
        messageListenerList.add(messageListener);
    }

    public CommonMessageHandler(List<MessageListener<?>> messageListenerList) {
        this.messageListenerList = messageListenerList;
    }
}
