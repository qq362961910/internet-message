package com.jy.im.server;

import com.jy.im.base.component.daemon.AbstractDaemonServer;
import com.jy.im.base.component.daemon.listener.DaemonListener;
import com.jy.im.base.component.launcher.Launcher;
import com.jy.im.server.initializer.NettyTcpServerInitializer;
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

public class TcpMessageServer extends AbstractDaemonServer {

    private Logger logger = LoggerFactory.getLogger(TcpMessageServer.class);

    private Channel serverChannel;
    private NettyTcpServerInitializer channelInitializer;

    @Override
    public void doStart(Launcher launcher) {
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
            //调用监听器#start()
            if (!demonListenerList.isEmpty()) {
                demonListenerList.forEach(listener -> listener.startup(this));
            }
            if (launcher != null) {
                launcher.serverStartSuccess(this);
            }
            serverChannel = future.channel();
            logger.info("TCP Server: {} has been started successfully, port: {}", name, port);
            serverChannel.closeFuture().sync().addListener(closeFuture -> {
                //调用监听器#close()
                if (!demonListenerList.isEmpty()) {
                    for (DaemonListener listener : demonListenerList) {
                        listener.close(TcpMessageServer.this);
                    }
                }
            });
        } catch (Exception e) {
            logger.error(String.format("TCP Server: %s Down, port: %d ", name, port), e);
        } finally {
            if (launcher != null) {
                launcher.serverShutdownSuccess(TcpMessageServer.this);
            }
            logger.info("[TCP Server]: {} closed, port: {}", name, port);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void doClose(Launcher launcher) {
        if (serverChannel != null) {
            serverChannel.close();
        }
    }

    public TcpMessageServer(String name, int port, List<DaemonListener> demonListenerList, NettyTcpServerInitializer channelInitializer) {
        super(name, port);
        if (demonListenerList != null && !demonListenerList.isEmpty()) {
            this.demonListenerList.addAll(demonListenerList);
        }
        this.channelInitializer = channelInitializer;
    }
}
