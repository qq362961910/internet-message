package com.jy.im.server.tcp.handler;

import com.jy.im.common.constants.CommonMessageType;
import com.jy.im.common.constants.MessageIdType;
import com.jy.im.common.entity.CommonMessage;
import com.jy.im.server.resource.TicketsHolder;
import com.jy.im.service.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CommonMessageHandler extends SimpleChannelInboundHandler<CommonMessage> {

    private Logger logger = LoggerFactory.getLogger(CommonMessageHandler.class);

    @Autowired
    private TicketsHolder ticketsHolder;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CommonMessage msg) throws Exception {
        logger.info("receive a message, type: {}", CommonMessageType.getCommonMessageType(msg.getMessageType()));
        logger.info("length: {}", msg.getLength());
        logger.info("fromId: {}", msg.getFromId());
        logger.info("toId: {}", msg.getToId());
        logger.info("message content: {}", new String(msg.getContent()));

        //用户消息
        if (msg.getFromIdType() == MessageIdType.USER_ID_TYPE.value) {
            //ticket检查
            byte[] ticket = msg.getTicket();
            if(ticket == null) {
                logger.error("receive a invalid message:\r\n {}", msg);
                ctx.close();
                return;
            }
            User user = ticketsHolder.getUserByTicket(new String(ticket));
            if(user == null) {
                logger.error("expired ticket, message:\r\n {}", msg);

            }
            logger.info("handle a user message from: userId: {}", user.getId());
        }
        //其他消息
        else {
            logger.error("unhandled message, message type: {}", CommonMessageType.getCommonMessageType(msg.getMessageType()));
        }
    }

}
