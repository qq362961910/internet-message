package com.jy.im.client.tcp.netty;

import com.jy.im.base.component.daemon.client.AbstractDaemonClient;
import com.jy.im.client.tcp.netty.initializer.NettyTcpClientInitializer;
import com.jy.im.client.tcp.netty.listener.NettyTcpClientDaemonListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpMessageClient extends AbstractDaemonClient<NettyTcpClientDaemonListener> {

    private Logger logger = LoggerFactory.getLogger(TcpMessageClient.class);

    private NettyTcpClientInitializer nettyTcpClientInitializer;

    @Override
    public void configBootstrap(Bootstrap bootstrap) {
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                 .handler(nettyTcpClientInitializer);
        logger.info("[TcpMessageClient]:{} config bootstrap ok", name);
    }
    public TcpMessageClient(String host, int port, NettyTcpClientInitializer nettyTcpClientInitializer) {
        super("netty-tcp-client", host, port);
        this.nettyTcpClientInitializer = nettyTcpClientInitializer;
    }
}
