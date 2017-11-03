package com.jy.im.base.component.daemon;


import com.jy.im.base.component.launcher.Launcher;

/**
 * 守护服务
 */
public interface Daemon {

    /**
     * 启动
     */
    void start(Launcher launcher);

    /**
     * 结束
     */
    void shutdown(Launcher launcher);

    /**
     * after start
     */

    void afterStart();

    /**
     * after stop
     */
    void afterShutdown();
}
