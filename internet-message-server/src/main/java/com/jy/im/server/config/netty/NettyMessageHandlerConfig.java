package com.jy.im.server.config.netty;

import com.jy.im.server.tcp.netty.handler.CommonLoginMessageHandler;
import com.jy.im.server.tcp.netty.handler.CommonUserStringMessageHandler;
import com.jy.im.service.TicketService;
import com.jy.im.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NettyMessageHandlerConfig {

    @Scope("prototype")
    @Bean
    public CommonLoginMessageHandler commonLoginMessageHandler(UserService userService, TicketService ticketService) {
        return new CommonLoginMessageHandler(userService, ticketService);
    }

    @Scope("prototype")
    @Bean
    public CommonUserStringMessageHandler commonUserStringMessageHandler(TicketService ticketService) {
        return new CommonUserStringMessageHandler(ticketService);
    }
}
