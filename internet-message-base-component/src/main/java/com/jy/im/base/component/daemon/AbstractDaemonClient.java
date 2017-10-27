package com.jy.im.base.component.daemon;


import com.jy.im.base.component.launcher.Launcher;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public abstract class AbstractDaemonClient implements Daemon {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractDaemonClient.class);

    protected String name;
    protected String host;
    protected int port;

    @Override
    public void start(Launcher launcher) {
        doStart(launcher);
    }

    @Override
    public void close(Launcher launcher) {
        doClose(launcher);
    }

    /**
     * 服务器会阻塞在这里
     */
    public abstract void doStart(Launcher launcher);

    /**
     *
     * */
    public abstract void doClose(Launcher launcher);

    public AbstractDaemonClient(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
