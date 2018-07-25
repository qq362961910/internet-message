package com.jy.im.server.config;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.launcher.DefaultLauncher;
import com.jy.im.base.component.launcher.DefaultLauncherListener;
import com.jy.im.base.component.launcher.LauncherListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class LauncherConfig {

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

}
