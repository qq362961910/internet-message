package com.jy.im.base.component.daemon;

import com.jy.im.base.component.daemon.listener.DaemonListener;
import com.jy.im.base.component.launcher.Launcher;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaemonServer<Listener extends DaemonListener> implements Daemon {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractDaemonServer.class);

    protected List<Listener> demonListenerList = new ArrayList<>();

    protected String name;
    protected int port;

    public AbstractDaemonServer(String name, int port) {
        this.name = name;
        this.port = port;
    }

    @Override
    public void afterStart() {
        //调用监听器#start()
        if (!demonListenerList.isEmpty()) {
            demonListenerList.forEach(listener -> listener.startup(this));
        }
    }
    @Override
    public void afterShutdown() {
        //调用监听器#start()
        if (!demonListenerList.isEmpty()) {
            for (DaemonListener listener : demonListenerList) {
                listener.close(this);
            }
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
