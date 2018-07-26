package com.jy.im.server.tcp.netty.handler;

import com.jy.im.common.constants.MessageSource;
import com.jy.im.common.constants.MessageType;
import com.jy.im.common.entity.TicketInvalidServerNotificationMessage;
import com.jy.im.common.entity.UserStringMessage;
import com.jy.im.service.TicketService;
import com.jy.im.service.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUserStringMessageHandler extends SimpleChannelInboundHandler<UserStringMessage> {

    private Logger logger = LoggerFactory.getLogger(CommonUserStringMessageHandler.class);

    private TicketService ticketService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UserStringMessage msg) {
        logger.info("receive a message, type: {}", MessageType.getCommonMessageType(msg.getMessageType()));
        logger.info("fromId: {}", msg.getFromId());
        logger.info("toId: {}", msg.getToId());
        logger.info("message content: {}", new String(msg.getContent()));

        //用户消息
        if (msg.getFromIdType() == MessageSource.USER_ID_TYPE.value) {
            //ticket检查
            byte[] ticket = msg.getTicket();
            if (ticket == null) {
                logger.error("receive a invalid message:\r\n {}", msg);
                ctx.close();
                return;
            }
            User user = ticketService.getUserByTicket(new String(ticket));
            if (user == null) {
                logger.error("expired ticket, message:\r\n {}", msg);
                TicketInvalidServerNotificationMessage ticketInvalidServerNotificationMessage = new TicketInvalidServerNotificationMessage();
                ctx.writeAndFlush(ticketInvalidServerNotificationMessage);
            } else {
                logger.info("handle a user message from: userId: {}", user.getId());
            }
        }
        //其他消息
        else {
            logger.error("unhandled message, message type: {}", MessageType.getCommonMessageType(msg.getMessageType()));
        }
    }

    public CommonUserStringMessageHandler(TicketService ticketService) {
        super(true);
        this.ticketService = ticketService;
    }
}
