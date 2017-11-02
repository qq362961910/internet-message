package com.jy.im.base.component.launcher;


import com.jy.im.base.component.daemon.Daemon;

/**
 * 启动器
 */
public interface Launcher {

    /**
     * 启动
     */
    void startup();

    /**
     * 停止
     */
    void close();

    /**
     * 启动器启动回调
     */
    void daemonStartSuccess(Daemon server);

    /**
     * 启动器停止回调
     */
    void daemonShutdownSuccess(Daemon server);
}
