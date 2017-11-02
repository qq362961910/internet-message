package com.jy.im.server.tcp.listener;

import com.jy.im.base.component.daemon.Daemon;
import com.jy.im.base.component.daemon.listener.DaemonListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class NettyTcpServerDaemonListener implements DaemonListener {

    private static final Logger logger = LoggerFactory.getLogger(NettyTcpServerDaemonListener.class);

    @Override
    public void startup(Daemon daemon) {
        logger.info("NettyTcpServerDaemonListener run startup()....");
    }

    @Override
    public void close(Daemon daemon) {
        logger.info("NettyTcpServerDaemonListener run close()....");
    }
}
