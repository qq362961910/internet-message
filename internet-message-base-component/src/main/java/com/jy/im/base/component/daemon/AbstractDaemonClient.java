package com.jy.im.base.component.daemon;


import com.jy.im.base.component.daemon.listener.DaemonListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaemonClient<Listener extends DaemonListener> implements Daemon {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractDaemonClient.class);

    protected List<Listener> demonListenerList = new ArrayList<>();

    protected final String name;
    protected final String host;
    protected final int port;

    @Override
    public void afterStart() {
        //调用监听器#start()
        if (!demonListenerList.isEmpty()) {
            demonListenerList.forEach(listener -> listener.startup(this));
        }
    }

    @Override
    public void afterShutdown() {
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public AbstractDaemonClient(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }
}
