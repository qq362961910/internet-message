package com.jy.im.server.config;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyServerMessageAnalyser;
import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.base.component.launcher.DefaultLauncherListener;
import com.jy.im.base.component.launcher.LauncherListener;
import com.jy.im.server.helper.ApplicationContextHelper;
import com.jy.im.server.tcp.netty.NettyTcpMessageServer;
import com.jy.im.server.tcp.netty.handler.CommonLoginMessageHandler;
import com.jy.im.server.tcp.netty.handler.CommonUserStringMessageHandler;
import com.jy.im.server.tcp.netty.initializer.NettyTcpServerInitializer;
import com.jy.im.server.tcp.netty.listener.NettyTcpServerDaemonListener;
import com.jy.im.server.tcp.netty.writer.CommonLoginFailResponseMessageWriter;
import com.jy.im.server.tcp.netty.writer.CommonLoginSuccessResponseMessageWriter;
import com.jy.im.server.udp.NettyUdpDaemonListener;
import com.jy.im.server.udp.decoder.NettyUdpDecoder;
import com.jy.im.server.udp.decoder.UdpTransferToTcpDecoder;
import com.jy.im.service.TicketService;
import com.jy.im.service.UserService;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class ServerConfig {


    @Scope("prototype")
    @Bean
    public NettyTcpServerInitializer nettyTcpServerInitializer(ApplicationContextHelper applicationContextHelper) {
        return new NettyTcpServerInitializer(applicationContextHelper);
    }

    /**
     * netty common message analyser
     */
    @Bean
    public NettyServerMessageAnalyser nettyCommonMessageAnalyser() {
        return new NettyServerMessageAnalyser();
    }

    /**
     * netty message analyser manager
     */
    @Bean
    public NettyMessageAnalyserManager nettyMessageAnalyserManager(List<NettyMessageAnalyser> nettyMessageAnalyserList) {
        return new NettyMessageAnalyserManager(nettyMessageAnalyserList);
    }

    /**
     * netty tcp decoder
     */
    @Scope("prototype")
    @Bean
    public NettyTcpDecoder nettyTcpDecoder(NettyMessageAnalyserManager nettyMessageAnalyserManager) {
        return new NettyTcpDecoder(nettyMessageAnalyserManager);
    }

    /**
     * netty tcp server
     */
    @Scope("prototype")
    @Bean
    public NettyTcpMessageServer tcpMessageServer(ApplicationContextHelper applicationContextHelper,List<NettyTcpServerDaemonListener> demonListenerList) {
        return new NettyTcpMessageServer("tcp-server", 5000, demonListenerList, nettyTcpServerInitializer(applicationContextHelper));
    }

    @Bean
    public DefaultLauncher defaultLauncher(List<LauncherListener> launcherListenerList, List<Daemon> daemonList) {
        DefaultLauncher defaultLauncher = new DefaultLauncher();
        //launch listener
        defaultLauncher.setLauncherListenerList(launcherListenerList);
        //daemon list
        defaultLauncher.addDaemonList(daemonList);
        return defaultLauncher;
    }

    @Scope("prototype")
    @Bean
    public DefaultLauncherListener defaultLauncherListener() {
        return new DefaultLauncherListener();
    }

    @Scope("prototype")
    @Bean
    public UdpTransferToTcpDecoder udpTransferToTcpDecoder(NettyTcpDecoder nettyTcpDecoder) {
        return new UdpTransferToTcpDecoder(nettyTcpDecoder);
    }

    @Scope("prototype")
    @Bean
    public CommonLoginFailResponseMessageWriter commonLoginFailResponseMessageWriter() {
        return new CommonLoginFailResponseMessageWriter();
    }

    @Scope("prototype")
    @Bean
    public NettyUdpDecoder nettyUdpDecoder(MessageToMessageDecoder<ByteBuf> decoder) {
        return new NettyUdpDecoder(decoder);
    }

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

    @Scope("prototype")
    @Bean
    public CommonLoginSuccessResponseMessageWriter commonLoginSuccessResponseMessageWriter() {
        return new CommonLoginSuccessResponseMessageWriter();
    }

}
