package com.jy.im.server.tcp.netty.listener;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.DaemonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyTcpServerDaemonListener implements DaemonListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyTcpServerDaemonListener.class);

    @Override
    public void afterStartup(Daemon daemon) {
        logger.info("NettyTcpServerDaemonListener run afterStartup()....");
    }

    @Override
    public void afterShutdown(Daemon daemon) {
        logger.info("NettyTcpServerDaemonListener run afterShutdown()....");
    }
}
