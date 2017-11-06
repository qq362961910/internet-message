package com.jy.im.server.udp;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.DaemonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class NettyUdpDaemonListener implements DaemonListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyUdpDaemonListener.class);

    @Override
    public void startup(Daemon server) {
        logger.info(server.getClass() + " start....");
    }

    @Override
    public void close(Daemon server) {
        logger.info(server.getClass() + " stop....");
    }
}
