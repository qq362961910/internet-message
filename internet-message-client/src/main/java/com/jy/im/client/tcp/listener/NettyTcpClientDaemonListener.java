package com.jy.im.client.tcp.listener;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.listener.DaemonListener;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4JLoggerFactory;

public class NettyTcpClientDaemonListener implements DaemonListener {

    private static final InternalLogger logger = Log4JLoggerFactory.getInstance(NettyTcpClientDaemonListener.class);

    @Override
    public void startup(Daemon daemon) {
        logger.info("NettyTcpClientDaemonListener run startup()....");
    }

    @Override
    public void close(Daemon daemon) {
        logger.info("NettyTcpClientDaemonListener run startup()....");
    }
}
