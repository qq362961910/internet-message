package com.jy.im.server.tcp.handler;

import com.jy.im.common.constants.SystemEvent;
import com.jy.im.common.entity.LoginMessage;
import com.jy.im.common.entity.SystemMessage;
import com.jy.im.server.resource.TicketsHolder;
import com.jy.im.service.UserService;
import com.jy.im.service.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginMessageHandler extends SimpleChannelInboundHandler<LoginMessage> {

    private static final Logger logger = LoggerFactory.getLogger(LoginMessageHandler.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TicketsHolder ticketsHolder;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginMessage msg) throws Exception {
        long userId = msg.getUserId();
        String password = new String(msg.getPassword());
        User user = userService.queryUserByUserIdAndPassword(userId, password);
        SystemMessage systemMessage = new SystemMessage();
        if (user == null) {
            systemMessage.setEvent(SystemEvent.LOGIN_FAIL.value);
            logger.info("userId: {}, submit wrong password: {}", userId, password);
        }
        else {
            byte[] ticket = String.valueOf(System.currentTimeMillis()).getBytes();
            ticketsHolder.addUserTicket(user, new String(ticket));
            systemMessage.setEvent(SystemEvent.LOGIN_SUCCESS.value);
            systemMessage.setContent(ticket);
        }
        ctx.write(systemMessage);
    }
}
