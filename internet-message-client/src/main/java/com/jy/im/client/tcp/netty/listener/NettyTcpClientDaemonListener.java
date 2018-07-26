package com.jy.im.client.tcp.netty.listener;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.DaemonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyTcpClientDaemonListener implements DaemonListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyTcpClientDaemonListener.class);

    @Override
    public void afterStartup(Daemon daemon) {
        logger.info("NettyTcpClientDaemonListener run afterStartup()....");
    }

    @Override
    public void afterShutdown(Daemon daemon) {
        logger.info("NettyTcpClientDaemonListener run afterShutdown()....");
    }
}
