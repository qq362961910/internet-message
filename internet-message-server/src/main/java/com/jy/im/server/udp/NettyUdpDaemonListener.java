package com.jy.im.server.udp;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.DaemonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyUdpDaemonListener implements DaemonListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyUdpDaemonListener.class);

    @Override
    public void afterStartup(Daemon server) {
        logger.info(server.getClass() + " afterStartup....");
    }

    @Override
    public void afterShutdown(Daemon server) {
        logger.info(server.getClass() + " afterShutdown....");
    }
}
