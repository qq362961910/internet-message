package com.jy.im.server.config;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.base.component.launcher.listener.DefaultLauncherListener;
import com.jy.im.base.component.launcher.listener.LauncherListener;
import com.jy.im.server.tcp.TcpMessageServer;
import com.jy.im.server.tcp.initializer.NettyTcpServerInitializer;
import com.jy.im.server.tcp.listener.NettyTcpDaemonListener;
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
    private List<NettyTcpDaemonListener> demonListenerList;
    @Autowired
    private List<Daemon> daemonList;
    @Autowired
    private List<LauncherListener> launcherListenerList;

    @Scope("prototype")
    @Bean
    public TcpMessageServer tcpMessageServer() {
        return new TcpMessageServer("tcp-server", 5000, demonListenerList, nettyTcpServerInitializer);
    }

    @Bean
    public DefaultLauncher defaultLauncher() {
        DefaultLauncher defaultLauncher = new DefaultLauncher();
        //launch listener
        defaultLauncher.setLauncherListenerList(launcherListenerList);
        //daemon list
        defaultLauncher.setDaemonList(daemonList);
        return defaultLauncher;
    }
    @Scope("prototype")
    @Bean
    public DefaultLauncherListener defaultLauncherListener() {
        return new DefaultLauncherListener();
    }
}
