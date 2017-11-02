package com.jy.im.server.tcp;

import com.jy.im.base.component.daemon.AbstractDaemonServer;
import com.jy.im.base.component.daemon.listener.DaemonListener;
import com.jy.im.base.component.launcher.Launcher;
import com.jy.im.server.tcp.initializer.NettyTcpServerInitializer;
import com.jy.im.server.tcp.listener.NettyTcpDaemonListener;
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

public class TcpMessageServer extends AbstractDaemonServer<NettyTcpDaemonListener> {

    private Logger logger = LoggerFactory.getLogger(TcpMessageServer.class);

    private Channel serverChannel;
    private NettyTcpServerInitializer channelInitializer;

    @Override
    public void start(Launcher launcher) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(getPort()).sync();
            if (launcher != null) {
                launcher.daemonStartSuccess(this);
            }
            serverChannel = future.channel();
            logger.info("TCP Server: {} has been started successfully, port: {}", name, port);
            serverChannel.closeFuture().sync();
        } catch (Exception e) {
            logger.error(String.format("TCP Server: %s Down, port: %d ", name, port), e);
        } finally {
            if (launcher != null) {
                launcher.daemonShutdownSuccess(this);
            }
            logger.info("[TCP Server]: {} closed, port: {}", name, port);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void shutdown(Launcher launcher) {
        if (serverChannel != null) {
            serverChannel.close();
        }
    }

    public TcpMessageServer(String name, int port, List<NettyTcpDaemonListener> demonListenerList, NettyTcpServerInitializer channelInitializer) {
        super(name, port);
        if (demonListenerList != null && !demonListenerList.isEmpty()) {
            this.demonListenerList.addAll(demonListenerList);
        }
        this.channelInitializer = channelInitializer;
    }
}
