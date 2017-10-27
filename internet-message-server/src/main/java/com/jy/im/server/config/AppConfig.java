package com.jy.im.server.config;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.listener.DaemonListener;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.base.component.launcher.listener.LauncherListener;
import com.jy.im.server.TcpMessageServer;
import com.jy.im.server.initializer.NettyTcpServerInitializer;
import com.jy.im.server.listener.DefaultLauncherListener;
import com.jy.im.server.listener.NettyTcpDaemonListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Scope("prototype")
    @Bean
    public TcpMessageServer tcpMessageServer(NettyTcpDaemonListener nettyTcpDaemonListener, NettyTcpServerInitializer nettyTcpServerInitializer) {
        List<DaemonListener> demonListenerList = new ArrayList<>();
        demonListenerList.add(nettyTcpDaemonListener);
        return new TcpMessageServer("tcp-server", 5000, demonListenerList, nettyTcpServerInitializer);
    }

    @Bean
    public DefaultLauncher defaultLauncher(DefaultLauncherListener defaultLauncherListener, TcpMessageServer tcpMessageServer) {
        DefaultLauncher defaultLauncher = new DefaultLauncher();

        //launch listener
        List<LauncherListener> launcherListenerList = new ArrayList<>();
        launcherListenerList.add(defaultLauncherListener);
        defaultLauncher.setLauncherListenerList(launcherListenerList);

        //daemon list
        List<Daemon> daemonList = new ArrayList<>();
        daemonList.add(tcpMessageServer);
        defaultLauncher.setDaemonList(daemonList);

        return defaultLauncher;
    }
}
