package com.jy.im.server.tcp.handler;

import com.jy.im.common.constants.CommonMessageType;
import com.jy.im.common.entity.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CommonMessageHandler extends SimpleChannelInboundHandler<CommonMessage> {

    private Logger logger = LoggerFactory.getLogger(CommonMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CommonMessage msg) throws Exception {
        logger.info("receive a message, type: {}, length: {},", CommonMessageType.getCommonMessageType(msg.getMessageType()), msg.getLength());
        logger.info("message content: {}", new String(msg.getContent()));
    }

}
