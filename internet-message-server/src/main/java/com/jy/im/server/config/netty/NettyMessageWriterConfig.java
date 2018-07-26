package com.jy.im.server.config.netty;

import com.jy.im.base.component.writer.tcp.netty.NettyCommonServerNotificationMessageWriter;
import com.jy.im.server.tcp.netty.writer.CommonLoginFailResponseMessageWriter;
import com.jy.im.server.tcp.netty.writer.CommonLoginSuccessResponseMessageWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NettyMessageWriterConfig {

    @Scope("prototype")
    @Bean
    public CommonLoginSuccessResponseMessageWriter commonLoginSuccessResponseMessageWriter() {
        return new CommonLoginSuccessResponseMessageWriter();
    }

    @Scope("prototype")
    @Bean
    public CommonLoginFailResponseMessageWriter commonLoginFailResponseMessageWriter() {
        return new CommonLoginFailResponseMessageWriter();
    }

    @Scope("prototype")
    @Bean
    public NettyCommonServerNotificationMessageWriter nettyCommonServerNotificationMessageWriter() {
        return new NettyCommonServerNotificationMessageWriter();
    }
}
