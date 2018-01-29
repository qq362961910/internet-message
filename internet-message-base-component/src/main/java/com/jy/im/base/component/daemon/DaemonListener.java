package com.jy.im.base.component.daemon;


/**
 * 守护服务监听器
 * */
public interface DaemonListener {

    /**
     * 守护服务启动后
     * */
    void afterStartup(Daemon daemon);

    /**
     * 守护服务停止后
     * */
    void afterShutdown(Daemon daemon);
}
