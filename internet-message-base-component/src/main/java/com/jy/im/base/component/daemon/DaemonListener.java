package com.jy.im.base.component.daemon;


public interface DaemonListener {

    void afterStartup(Daemon daemon);

    void afterShutdown(Daemon daemon);
}
