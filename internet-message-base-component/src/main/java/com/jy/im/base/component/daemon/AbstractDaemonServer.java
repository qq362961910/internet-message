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

    @Override
    public void start(Launcher launcher) {
        doStart(launcher);
        //listener::start()
        //因为服务器启动会阻塞，监听器不能在这里执行,只能在具体的服务器实例里面启动
    }

    @Override
    public void close(Launcher launcher) {
        doClose(launcher);
        //listener::close()
        //在具体的服务器实例里面启动
    }

    /**
     * 阻塞
     * 启动服务器
     */
    public abstract void doStart(Launcher launcher);

    /**
     * 阻塞
     * 关闭服务器
     */
    public abstract void doClose(Launcher launcher);

    public AbstractDaemonServer() {
    }

    public AbstractDaemonServer(int port) {
        this.port = port;
    }

    public AbstractDaemonServer(String name, int port) {
        this.name = name;
        this.port = port;
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
