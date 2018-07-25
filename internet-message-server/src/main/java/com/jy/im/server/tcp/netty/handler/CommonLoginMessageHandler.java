package com.jy.im.server.tcp.netty.handler;

import com.jy.im.common.constants.CommonMessageCode;
import com.jy.im.common.entity.CommonLoginFailResponseMessage;
import com.jy.im.common.entity.CommonLoginRequestMessage;
import com.jy.im.common.entity.CommonLoginSuccessResponseMessage;
import com.jy.im.server.resource.TicketsHolder;
import com.jy.im.service.UserService;
import com.jy.im.service.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class CommonLoginMessageHandler extends SimpleChannelInboundHandler<CommonLoginRequestMessage> {

    private static final Logger logger = LoggerFactory.getLogger(CommonLoginMessageHandler.class);

    private UserService userService;
    private TicketsHolder ticketsHolder;

    public CommonLoginMessageHandler(UserService userService, TicketsHolder ticketsHolder) {
        this.userService = userService;
        this.ticketsHolder = ticketsHolder;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CommonLoginRequestMessage msg) {
        long userId = msg.getUserId();
        String password = new String(msg.getPassword());
        User user = userService.queryUserByUserIdAndPassword(userId, password);
        if (user == null) {
            logger.info("userId: {}, submit wrong password: {}", userId, password);
            CommonLoginFailResponseMessage response = new CommonLoginFailResponseMessage();
            response.setMessageId(msg.getMessageId());
            response.setErrorCode(CommonMessageCode.USERNAME_OR_PASSWORD_ERROR);
            response.setUserId(userId);
            response.setPassword(password);
            ctx.writeAndFlush(response).addListener(f -> {
                if(!f.isSuccess()) {
                    logger.error("", f.cause());
                }
            });
        } else {
            CommonLoginSuccessResponseMessage response = new CommonLoginSuccessResponseMessage();
            byte[] ticket = TicketsHolder.generateTicket().getBytes();
            ticketsHolder.addUserTicket(user, new String(ticket));
            response.setMessageId(msg.getMessageId());
            response.setErrorCode(CommonMessageCode.SUCCESS);
            response.setUserId(userId);
            response.setTicket(ticket);
            logger.info("user: {} login success with ticket: {}", userId, new String(ticket));
            ctx.writeAndFlush(response).addListener(f -> {
                if(!f.isSuccess()) {
                    logger.error("", f.cause());
                }
            });
        }
    }
}
