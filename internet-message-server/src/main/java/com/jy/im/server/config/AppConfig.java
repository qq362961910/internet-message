package com.jy.im.server.config;

import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyser;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyMessageAnalyserManager;
import com.jy.im.base.component.analyser.message.tcp.netty.NettyServerMessageAnalyser;
import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.decoder.tcp.netty.NettyTcpDecoder;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.base.component.launcher.DefaultLauncherListener;
import com.jy.im.base.component.launcher.LauncherListener;
import com.jy.im.server.tcp.netty.NettyTcpMessageServer;
import com.jy.im.server.tcp.netty.initializer.NettyTcpServerInitializer;
import com.jy.im.server.tcp.netty.listener.NettyTcpServerDaemonListener;
import com.jy.im.service.UserService;
import com.jy.im.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class AppConfig {

    @Autowired
    private NettyTcpServerInitializer nettyTcpServerInitializer;
    @Autowired
    private List<NettyTcpServerDaemonListener> demonListenerList;
    @Autowired
    private List<Daemon> daemonList;
    @Autowired
    private List<LauncherListener> launcherListenerList;


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
    public NettyMessageAnalyserManager nettyMessageAnalyserManager(NettyMessageAnalyser... nettyMessageAnalysers) {
        return new NettyMessageAnalyserManager(nettyMessageAnalysers);
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
    public NettyTcpMessageServer tcpMessageServer() {
        return new NettyTcpMessageServer("tcp-server", 5000, demonListenerList, nettyTcpServerInitializer);
    }

    @Bean
    public DefaultLauncher defaultLauncher() {
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

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
