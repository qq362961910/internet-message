package com.jy.im.server.config.netty;

import com.jy.im.server.tcp.netty.listener.NettyTcpServerDaemonListener;
import com.jy.im.server.udp.NettyUdpDaemonListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class NettyListenerConfig {

    @Scope("prototype")
    @Bean
    public NettyUdpDaemonListener nettyUdpDaemonListener() {
        return new NettyUdpDaemonListener();
    }

    @Scope("prototype")
    @Bean
    public NettyTcpServerDaemonListener nettyTcpServerDaemonListener() {
        return new NettyTcpServerDaemonListener();
    }
}
