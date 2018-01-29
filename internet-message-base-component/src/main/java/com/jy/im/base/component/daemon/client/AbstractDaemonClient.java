package com.jy.im.base.component.daemon.client;


import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.DaemonListener;
import com.jy.im.base.component.launcher.Launcher;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDaemonClient<Listener extends DaemonListener> implements Daemon {

    private InternalLogger logger = Log4JLoggerFactory.getInstance(AbstractDaemonClient.class);

    /**
     * 监听器
     * */
    private List<Listener> demonListenerList = new ArrayList<>();

    /**
     * work group
     * */
    private EventLoopGroup workerGroup;

    /**
     * bootstrap
     * */
    private Bootstrap bootstrap;

    /**
     * client name
     * */
    protected final String name;

    /**
     * connect host
     * */
    private final String host;

    /**
     * connect port
     * */
    private final int port;

    private Channel clientChannel;

    @Override
    public void beforeStart() {
        workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                 .channel(NioSocketChannel.class);
        configBootstrap(bootstrap);
    }

    /**
     * 配置bootstrap
     * */
    public abstract void configBootstrap(Bootstrap bootstrap);

    @Override
    public void start(Launcher launcher) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            clientChannel = channelFuture.channel();
            if (launcher != null) {
                launcher.daemonStartSuccess(this);
            }
            clientChannel.closeFuture().sync();
        } catch (Exception e) {
            logger.error("[Client]: {} throws a exception: {}", name, e);
        } finally {
            logger.info("[Client]: {} closed, port: {} ", name, port);
            workerGroup.shutdownGracefully();
            if (launcher != null) {
                launcher.daemonShutdownSuccess(this);
            }
        }
    }

    @Override
    public void afterStart() {
        //调用监听器#afterStart()
        if (!demonListenerList.isEmpty()) {
            demonListenerList.forEach(listener -> listener.afterStartup(this));
        }
    }

    @Override
    public void beforeShutdown() {

    }

    @Override
    public void shutdown(Launcher launcher) {
        if (clientChannel != null) {
            clientChannel.close();
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

    /**
     * write any message
     * */
    public void writeMessage(Object message) {
        clientChannel.writeAndFlush(message);
    }
}
