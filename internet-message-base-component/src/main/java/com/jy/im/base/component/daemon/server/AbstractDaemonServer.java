package com.jy.im.base.component.daemon.server;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.DaemonListener;
import com.jy.im.base.component.launcher.Launcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaemonServer<Listener extends DaemonListener> implements Daemon {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractDaemonServer.class);

    /**
     * server listener
     * */
    protected List<Listener> demonListenerList = new ArrayList<>();

    /**
     * server name
     * */
    protected String name;

    /**
     * port to listen
     * */
    protected int port;

    /**
     * boss group
     * */
    private EventLoopGroup bossGroup;

    /**
     * worker group
     * */
    private EventLoopGroup workerGroup;

    /**
     * server bootstrap
     * */
    private ServerBootstrap bootstrap;

    /**
     * server channel
     * */
    private Channel serverChannel;

    @Override
    public void beforeStart() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class);
        configBootstrap(bootstrap);
    }

    /**
     * 配置bootstrap
     * */
    public abstract void configBootstrap(ServerBootstrap serverBootstrap);

    @Override
    public void start(Launcher launcher) {
        try {
            ChannelFuture future = bootstrap.bind(getPort()).sync();
            if (launcher != null) {
                launcher.daemonStartSuccess(this);
            }
            serverChannel = future.channel();
            logger.info("TCP Server: {} has been started successfully, port: {}", name, port);
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("[TCP Server]: {} closed, port: {}", name, port);
            if (launcher != null) {
                launcher.daemonShutdownSuccess(this);
            }
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void afterStart() {
        //调用监听器#afterStartup()
        if (!demonListenerList.isEmpty()) {
            demonListenerList.forEach(listener -> listener.afterStartup(AbstractDaemonServer.this));
        }
    }

    @Override
    public void beforeShutdown() {

    }

    @Override
    public void shutdown(Launcher launcher) {
        if (serverChannel != null) {
            serverChannel.close();
        }
    }

    @Override
    public void afterShutdown() {
        //调用监听器#start()
        if (!demonListenerList.isEmpty()) {
            demonListenerList.forEach(listener -> listener.afterShutdown(AbstractDaemonServer.this));
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

    public AbstractDaemonServer(String name, int port, List<Listener> demonListenerList) {
        this.name = name;
        this.port = port;
        if (demonListenerList != null && !demonListenerList.isEmpty()) {
            this.demonListenerList.addAll(demonListenerList);
        }
    }
}
