package com.jy.im.server.tcp.netty.handler;

import com.jy.im.common.constants.ResponseCode;
import com.jy.im.common.entity.LoginMessageRequest;
import com.jy.im.common.entity.LoginMessageResponse;
import com.jy.im.server.resource.TicketsHolder;
import com.jy.im.service.UserService;
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
public class LoginMessageHandler extends SimpleChannelInboundHandler<LoginMessageRequest> {

    private static final Logger logger = LoggerFactory.getLogger(LoginMessageHandler.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TicketsHolder ticketsHolder;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginMessageRequest msg) throws Exception {
        long userId = msg.getUserId();
        String password = new String(msg.getPassword());
        User user = userService.queryUserByUserIdAndPassword(userId, password);
        LoginMessageResponse response = new LoginMessageResponse();
        if (user == null) {
            response.setCode(ResponseCode.USERNAME_OR_PASSWORD_ERROR.value);
            logger.info("userId: {}, submit wrong password: {}", userId, password);
        } else {
            response.setCode(ResponseCode.SUCCESS.value);
            byte[] ticket = TicketsHolder.generateTicket().getBytes();
            ticketsHolder.addUserTicket(user, new String(ticket));
            response.setTicket(ticket);
        }
        ctx.writeAndFlush(response);
        logger.info("user: {} login success", userId);
    }
}
