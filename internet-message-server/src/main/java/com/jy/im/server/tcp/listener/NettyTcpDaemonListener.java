package com.jy.im.server.tcp.listener;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.listener.DaemonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class NettyTcpDaemonListener implements DaemonListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyTcpDaemonListener.class);

    @Override
    public void startup(Daemon server) {
        logger.info("NettyTcpDaemonListener run startup()....");
    }

    @Override
    public void close(Daemon server) {
        logger.info("NettyTcpDaemonListener run close()....");
    }
}
