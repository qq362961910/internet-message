package com.jy.im.base.component.daemon.listener;


import com.jy.im.base.component.daemon.Daemon;

public interface DaemonListener {

    void startup(Daemon server);

    void close(Daemon server);
}
