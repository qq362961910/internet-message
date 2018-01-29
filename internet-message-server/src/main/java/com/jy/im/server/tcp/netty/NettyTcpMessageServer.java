package com.jy.im.server.tcp.netty;

import com.jy.im.base.component.daemon.server.AbstractDaemonServer;
import com.jy.im.base.component.launcher.Launcher;
import com.jy.im.server.tcp.netty.initializer.NettyTcpServerInitializer;
import com.jy.im.server.tcp.netty.listener.NettyTcpServerDaemonListener;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NettyTcpMessageServer extends AbstractDaemonServer<NettyTcpServerDaemonListener> {

    private Logger logger = LoggerFactory.getLogger(NettyTcpMessageServer.class);

    private NettyTcpServerInitializer channelInitializer;

    @Override
    public void configBootstrap(ServerBootstrap serverBootstrap) {
        serverBootstrap.childHandler(channelInitializer)
                       .option(ChannelOption.SO_BACKLOG, 128)
                       .childOption(ChannelOption.SO_KEEPALIVE, true);
        logger.info("[NettyTcpMessageServer]:{} config bootstrap ok", name);
    }


    public NettyTcpMessageServer(String name, int port, List<NettyTcpServerDaemonListener> demonListenerList, NettyTcpServerInitializer channelInitializer) {
        super(name, port, demonListenerList);
        this.channelInitializer = channelInitializer;
    }
}
