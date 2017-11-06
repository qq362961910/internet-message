package com.jy.im.base.component.daemon;


public interface DaemonListener {

    void startup(Daemon daemon);

    void close(Daemon daemon);
}
