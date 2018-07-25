package com.jy.im.server.config.netty;

import com.jy.im.server.helper.ApplicationContextHelper;
import com.jy.im.server.tcp.netty.NettyTcpMessageServer;
import com.jy.im.server.tcp.netty.initializer.NettyTcpServerInitializer;
import com.jy.im.server.tcp.netty.listener.NettyTcpServerDaemonListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class NettyServerConfig {

    @Scope("prototype")
    @Bean
    public NettyTcpServerInitializer nettyTcpServerInitializer(ApplicationContextHelper applicationContextHelper) {
        return new NettyTcpServerInitializer(applicationContextHelper);
    }

    /**
     * netty tcp server
     */
    @Scope("prototype")
    @Bean
    public NettyTcpMessageServer tcpMessageServer(ApplicationContextHelper applicationContextHelper, List<NettyTcpServerDaemonListener> demonListenerList) {
        return new NettyTcpMessageServer("tcp-server", 5000, demonListenerList, nettyTcpServerInitializer(applicationContextHelper));
    }
}
