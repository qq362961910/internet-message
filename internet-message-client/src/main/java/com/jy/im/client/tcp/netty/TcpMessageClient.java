package com.jy.im.client.tcp.netty;

import com.jy.im.base.component.daemon.client.AbstractDaemonClient;
import com.jy.im.client.tcp.netty.initializer.NettyTcpClientInitializer;
import com.jy.im.client.tcp.netty.listener.NettyTcpClientDaemonListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;

public class TcpMessageClient extends AbstractDaemonClient<NettyTcpClientDaemonListener> {

    @Override
    public void configBootstrap(Bootstrap bootstrap) {
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                 .handler(new NettyTcpClientInitializer());
    }
    public TcpMessageClient(String host, int port) {
        super("netty-tcp-client", host, port);
    }
}
