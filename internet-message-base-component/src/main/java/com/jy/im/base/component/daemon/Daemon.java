package com.jy.im.base.component.daemon;


import com.jy.im.base.component.launcher.Launcher;

/**
 * 守护服务
 */
public interface Daemon {

    /**
     * before start
     * */
    void beforeStart();

    /**
     * 启动
     */
    void start(Launcher launcher);

    /**
     * after start
     */

    void afterStart();

    /**
     * before shutdown
     * */
    void beforeShutdown();

    /**
     * 结束
     */
    void shutdown(Launcher launcher);

    /**
     * after stop
     */
    void afterShutdown();
}
